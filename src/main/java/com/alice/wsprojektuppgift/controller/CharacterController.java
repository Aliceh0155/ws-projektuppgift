package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.model.CharacterModel;
import com.alice.wsprojektuppgift.service.CharacterApiService;
import com.alice.wsprojektuppgift.service.CharacterDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CharacterController {


  private final CharacterApiService characterApiService;
  private final CharacterDBService characterDBService;


  @Autowired
  public CharacterController(CharacterApiService characterApiService, CharacterDBService characterDBService) {
    this.characterApiService = characterApiService;
    this.characterDBService = characterDBService;
  }

  @GetMapping("/allCharactersFromApi")
  public ResponseEntity<List<CharacterModel>> getAllCharacters() {
    List<CharacterModel> characters = characterApiService.getAllCharacters();
    return ResponseEntity.ok(characters);
  }

  @GetMapping("/characterByHouse/{house}")
  public ResponseEntity<List<CharacterModel>> getCharacterByHouse(@PathVariable String house) {
    List<CharacterModel> characters = characterApiService.getCharactersByHouse(house);
    return ResponseEntity.ok(characters);
  }

  @GetMapping("/character/{id}")
  public ResponseEntity<CharacterModel> getCharacterById(@PathVariable String id) {

    CharacterModel character = characterApiService.getOneCharacterById(id);

    if (character != null) {
      return ResponseEntity.ok(character);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PostMapping("/saveCharactersToDatabase")
  public ResponseEntity<String> saveFirst100Characters() {
    characterDBService.charactersToDatabase();
    return ResponseEntity.ok("De första 100 karaktärerna har sparats.");
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


  @GetMapping("/getAllCharactersFromDatabase")
  public ResponseEntity<List<FavouriteCharacterEntity>> getAllCharactersFromDatabase() {
    List<FavouriteCharacterEntity> favourites = characterDBService.getAllCharacters();

    if (favourites.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(favourites);
  }
}
