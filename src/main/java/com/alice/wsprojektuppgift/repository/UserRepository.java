package com.alice.wsprojektuppgift.repository;

import com.alice.wsprojektuppgift.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

    Optional<CustomUser> findByUsername(String username);


}
