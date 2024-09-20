package com.alice.wsprojektuppgift.repository;

import com.alice.wsprojektuppgift.entity.FavouriteCharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteCharacterRepository extends JpaRepository<FavouriteCharacterEntity, Long> {
}
