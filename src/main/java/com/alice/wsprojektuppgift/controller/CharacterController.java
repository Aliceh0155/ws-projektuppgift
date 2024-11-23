package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.model.CharacterModel;
import com.alice.wsprojektuppgift.service.CharacterApiService;
import com.alice.wsprojektuppgift.service.CharacterDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// @PreAuthorize
//@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CharacterController {


    private final CharacterApiService characterApiService;
    private final CharacterDBService characterDBService;

    @Autowired
    public CharacterController(CharacterApiService characterApiService, CharacterDBService characterDBService) {
        this.characterApiService = characterApiService;
        this.characterDBService = characterDBService;
    }

    @GetMapping("/allCharacters")
    public ResponseEntity<List<CharacterModel>> getAllCharacters() {
        List<CharacterModel> characters = characterApiService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/characterByHouse/{house}")
    public ResponseEntity<List<CharacterModel>> getCharacterByHouse(@PathVariable String house) {
        List<CharacterModel> characters = characterApiService.getCharactersByHouse(house);
        return ResponseEntity.ok(characters);
    }

//    @GetMapping("/allCharacters")
//    public ResponseEntity<Mono<List<CharacterModel>>> getAllCharacters() {
//
//        return ResponseEntity.ok(hpwebClient.get()
//                .uri("/characters")
//                .retrieve()
//                .bodyToFlux(CharacterModel.class)
//                .collectList());
//    }
//
//
//    @GetMapping("/characterByHouse/{house}")
//    public ResponseEntity<Mono<List<CharacterModel>>> getCharacterByHouse(@PathVariable String house) {
//
//        return ResponseEntity.ok(hpwebClient.get()
//                .uri("/characters/house/{house}", house)
//                .retrieve()
//                .bodyToFlux(CharacterModel.class)
//                .collectList());
//    }


    @PostMapping("/addCharacter")
    public ResponseEntity<FavouriteCharacterEntity> addCharacter(@RequestBody FavouriteCharacterEntity favouriteCharacterEntity) {
        FavouriteCharacterEntity savedCharacter = characterDBService.addCharacter(favouriteCharacterEntity);
        return ResponseEntity.status(200).body(savedCharacter);
    }



    @PutMapping("/updateImage/{id}")
    public ResponseEntity<FavouriteCharacterEntity> updateImage(@PathVariable String id, @RequestBody String newImage) {
        FavouriteCharacterEntity updatedCharacter = characterDBService.updateImage(id, newImage);
        if (updatedCharacter != null) {
            return ResponseEntity.status(200).body(updatedCharacter);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/deleteCharacter/{id}")
    public ResponseEntity<FavouriteCharacterEntity> deleteCharacter(@PathVariable("id") String id) {
        FavouriteCharacterEntity deletedCharacter = characterDBService.deleteCharacter(id);
        if (deletedCharacter != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/getFavourites")
    public ResponseEntity<List<FavouriteCharacterEntity>> getFavourites() {
        List<FavouriteCharacterEntity> favourites = characterDBService.getFavourites();

        if (favourites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favourites);
    }


}
