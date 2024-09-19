package com.alice.wsprojektuppgift.repository;

import com.alice.wsprojektuppgift.model.Wand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WandRepository extends JpaRepository<Wand, Long> {
}