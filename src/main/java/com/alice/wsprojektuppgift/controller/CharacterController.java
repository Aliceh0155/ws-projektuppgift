package com.alice.wsprojektuppgift.controller;


import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.model.CharacterModel;
import com.alice.wsprojektuppgift.repository.FavouriteCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
public class CharacterController {

    private final FavouriteCharacterRepository favouriteCharacterRepository;
    private final WebClient hpwebClient;

    @Autowired
    public CharacterController(FavouriteCharacterRepository favouriteCharacterRepository, WebClient.Builder webClient) {
        this.favouriteCharacterRepository = favouriteCharacterRepository;
        this.hpwebClient = webClient
                .baseUrl("https://hp-api.herokuapp.com/api")
                .build();
    }

    @GetMapping("/allCharacters")
    public Mono<List<CharacterModel>> getAllCharacters() {

        return hpwebClient.get()
                .uri("/characters")
                .retrieve()
                .bodyToFlux(CharacterModel.class)
                .collectList();
    }


    @GetMapping("/characterByHouse/{house}")
    public ResponseEntity<Mono<List<CharacterModel>>> getCharacterByHouse(@PathVariable String house) {

        return ResponseEntity.ok(hpwebClient.get()
                .uri("/characters/house/{house}", house)
                .retrieve()
                .bodyToFlux(CharacterModel.class)
                .collectList());
    }

    @PostMapping("/addCharacter")
    public ResponseEntity<FavouriteCharacterEntity> addCharacter(@RequestBody FavouriteCharacterEntity favouriteCharacterEntity) {

        return ResponseEntity.status(201).body(favouriteCharacterRepository.save(favouriteCharacterEntity));
    }

    @PutMapping("/updateImage/{id}")
    public ResponseEntity<FavouriteCharacterEntity> updateImage(@PathVariable ("id") Long id, @RequestBody String newImage) {

        Optional<FavouriteCharacterEntity> character = favouriteCharacterRepository.findById(id);

        if (character.isPresent()) {
            character.get().setImage(newImage);
            favouriteCharacterRepository.save(character.get());
            return ResponseEntity.status(200).body(character.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteCharacter/{id}")
    public ResponseEntity<FavouriteCharacterEntity> deleteCharacter(@PathVariable ("id") Long id) {

        Optional<FavouriteCharacterEntity> character = favouriteCharacterRepository.findById(id);
        if (character.isPresent()) {
            favouriteCharacterRepository.delete(character.get());
            return ResponseEntity.status(200).body(character.get());
        }
        return ResponseEntity.notFound().build();
    }

}
