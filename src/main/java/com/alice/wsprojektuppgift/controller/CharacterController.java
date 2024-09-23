package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.model.CharacterModel;
import com.alice.wsprojektuppgift.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
public class CharacterController {


    private final WebClient hpwebClient;
    private final CharacterService characterService;

    @Autowired
    public CharacterController(WebClient.Builder webClient, CharacterService characterService) {

        this.hpwebClient = webClient
                .baseUrl("https://hp-api.herokuapp.com/api")
                .build();
        this.characterService = characterService;
    }

    @GetMapping("/allCharacters")
    public ResponseEntity<Mono<List<CharacterModel>>> getAllCharacters() {

        return ResponseEntity.ok(hpwebClient.get()
                .uri("/characters")
                .retrieve()
                .bodyToFlux(CharacterModel.class)
                .collectList());
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
        FavouriteCharacterEntity savedCharacter = characterService.addCharacter(favouriteCharacterEntity);
        return ResponseEntity.status(200).body(savedCharacter);
    }

    @PutMapping("/updateImage/{id}")
    public ResponseEntity<FavouriteCharacterEntity> updateImage(@PathVariable Long id, @RequestBody String newImage) {
        FavouriteCharacterEntity updatedCharacter = characterService.updateImage(id, newImage);
        if (updatedCharacter != null) {
            return ResponseEntity.status(200).body(updatedCharacter);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/deleteCharacter/{id}")
    public ResponseEntity<FavouriteCharacterEntity> deleteCharacter(@PathVariable("id") Long id) {
        FavouriteCharacterEntity deletedCharacter = characterService.deleteCharacter(id);
        if (deletedCharacter != null) {
            return ResponseEntity.status(200).body(deletedCharacter);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/getFavourites")
    public ResponseEntity<List<FavouriteCharacterEntity>> getFavourites() {
        List<FavouriteCharacterEntity> favourites = characterService.getFavourites();

        if (favourites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favourites);
    }


}
