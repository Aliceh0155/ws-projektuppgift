package com.alice.wsprojektuppgift.repository;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteCharacterRepository extends MongoRepository<FavouriteCharacterEntity, String> {
}
