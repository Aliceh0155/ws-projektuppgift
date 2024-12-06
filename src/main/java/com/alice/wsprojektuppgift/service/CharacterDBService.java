package com.alice.wsprojektuppgift.service;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.entity.WandEntity;
import com.alice.wsprojektuppgift.model.CharacterModel;
import com.alice.wsprojektuppgift.repository.IFavouriteCharacterRepository;
import jakarta.validation.constraints.AssertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterDBService {

  private final IFavouriteCharacterRepository favouriteCharacterRepository;
  private final CharacterApiService characterApiService;

  @Autowired
  public CharacterDBService(IFavouriteCharacterRepository favouriteCharacterRepository, CharacterApiService characterApiService) {
    this.favouriteCharacterRepository = favouriteCharacterRepository;
    this.characterApiService = characterApiService;
  }

  //Lägg till 100 karaktärer i databasen
  public void charactersToDatabase() {
    List<CharacterModel> characters = characterApiService.getFirst100Characters();
    List<FavouriteCharacterEntity> favouriteCharacters = characters.stream()
            .map(this::convertToFavouriteCharacterEntity)
            .collect(Collectors.toList());

    favouriteCharacterRepository.saveAll(favouriteCharacters);

  }

  //Omvandla model till databasentitet
  private FavouriteCharacterEntity convertToFavouriteCharacterEntity(CharacterModel character) {
    WandEntity wandEntity = new WandEntity(
            character.getWand().getWood(),
            character.getWand().getCore(),
            character.getWand().getLength()
    );

    return new FavouriteCharacterEntity(
            character.getId(),
            character.getName(),
            character.getSpecies(),
            character.getGender(),
            character.getHouse(),
            character.getDateOfBirth(),
            character.getYearOfBirth(),
            character.isWizard(),
            character.getAncestry(),
            character.getEyeColour(),
            character.getHairColour(),
            wandEntity,
            character.isHogwartsStudent(),
            character.isAlive(),
            character.getImage()
    );
  }

  //Uppdatera bild för karaktär
  public FavouriteCharacterEntity updateImage(String id, String newImage) {
    FavouriteCharacterEntity character = favouriteCharacterRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Character with id " + id + " not found"));

    character.setImage(newImage);
    return favouriteCharacterRepository.save(character);
  }

  //Ta bort en karaktär
  public FavouriteCharacterEntity deleteCharacter(String id) {
    Optional<FavouriteCharacterEntity> character = favouriteCharacterRepository.findById(id);
    if (character.isPresent()) {
      favouriteCharacterRepository.delete(character.get());
      return character.get();
    }
    throw new NoSuchElementException("Character with id " + id + " not found");
  }

//Hämta alla karaktärer från databasen
  public List<FavouriteCharacterEntity> getAllCharacters() {

    return favouriteCharacterRepository.findAll();
  }


}
