package com.alice.wsprojektuppgift.repository;

import com.alice.wsprojektuppgift.entity.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<CustomUser, String> {

  boolean existsByUsername(String username);

  Optional<CustomUser> findByUsername(String username);
}
