package com.ChallengesFerGomez.LiterAluraChallenge.repository;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {
          Optional<AutorEntity> findByNombre(String nombre);
}
