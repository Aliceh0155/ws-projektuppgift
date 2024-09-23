package com.alice.wsprojektuppgift.service;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import com.alice.wsprojektuppgift.repository.FavouriteCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CharacterService {

    private final FavouriteCharacterRepository favouriteCharacterRepository;

    @Autowired
    public CharacterService(FavouriteCharacterRepository favouriteCharacterRepository) {
        this.favouriteCharacterRepository = favouriteCharacterRepository;
    }

    public FavouriteCharacterEntity addCharacter(FavouriteCharacterEntity characterEntity) {
        return favouriteCharacterRepository.save(characterEntity);
    }

    public FavouriteCharacterEntity updateImage(Long id, String newImage) {
        Optional<FavouriteCharacterEntity> character = favouriteCharacterRepository.findById(id);
        if (character.isPresent()) {
            FavouriteCharacterEntity characterEntity = character.get();
            characterEntity.setImage(newImage);
            return favouriteCharacterRepository.save(characterEntity);
        }
        throw new NoSuchElementException("Character with id " + id + " not found");
    }

    public FavouriteCharacterEntity deleteCharacter(Long id) {
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
