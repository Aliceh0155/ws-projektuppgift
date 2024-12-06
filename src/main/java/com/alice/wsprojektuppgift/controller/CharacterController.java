package com.alice.wsprojektuppgift.controller;

import com.alice.wsprojektuppgift.config.security.CustomUserDetails;
import com.alice.wsprojektuppgift.config.security.JwtUtil;
import com.alice.wsprojektuppgift.entity.CustomUser;
import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.model.CharacterModel;
import com.alice.wsprojektuppgift.model.dto.CustomUserDTO;
import com.alice.wsprojektuppgift.model.dto.CustomUserLoginDTO;
import com.alice.wsprojektuppgift.repository.IUserRepository;
import com.alice.wsprojektuppgift.service.CharacterApiService;
import com.alice.wsprojektuppgift.service.CharacterDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// @PreAuthorize
@RestController
public class CharacterController {


  private final CharacterApiService characterApiService;
  private final CharacterDBService characterDBService;
  private final IUserRepository userRepository;


  @Autowired
  public CharacterController(CharacterApiService characterApiService, CharacterDBService characterDBService, IUserRepository userRepository) {
    this.characterApiService = characterApiService;
    this.characterDBService = characterDBService;
    this.userRepository = userRepository;
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
