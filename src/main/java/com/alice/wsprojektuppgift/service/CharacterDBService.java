package com.alice.wsprojektuppgift.service;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.repository.IFavouriteCharacterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CharacterDBService {

   private final IFavouriteCharacterRepository favouriteCharacterRepository;

    public CharacterDBService(IFavouriteCharacterRepository favouriteCharacterRepository) {
        this.favouriteCharacterRepository = favouriteCharacterRepository;
    }

    public FavouriteCharacterEntity addCharacter(FavouriteCharacterEntity characterEntity) {
        return favouriteCharacterRepository.save(characterEntity);  // Synkron version
    }


    public FavouriteCharacterEntity updateImage(String id, String newImage) {
        FavouriteCharacterEntity character = favouriteCharacterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Character with id " + id + " not found"));

        character.setImage(newImage);
        return favouriteCharacterRepository.save(character);
    }




    public FavouriteCharacterEntity deleteCharacter(String id) {
        Optional<FavouriteCharacterEntity> character = favouriteCharacterRepository.findById(id);
        if (character.isPresent()) {
            favouriteCharacterRepository.delete(character.get());
            return character.get();
        }
        throw new NoSuchElementException("Character with id " + id + " not found");
    }



    public List<FavouriteCharacterEntity> getFavourites() {

        return favouriteCharacterRepository.findAll();
    }


}
