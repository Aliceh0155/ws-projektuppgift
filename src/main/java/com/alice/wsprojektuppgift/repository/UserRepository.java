package com.alice.wsprojektuppgift.repository;

import com.alice.wsprojektuppgift.entity.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<CustomUser, String> {

    Optional<CustomUser> findByUsername(String username);


}
