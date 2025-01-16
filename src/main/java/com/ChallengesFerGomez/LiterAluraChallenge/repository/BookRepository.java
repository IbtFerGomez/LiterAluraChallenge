package com.ChallengesFerGomez.LiterAluraChallenge.repository;

import com.ChallengesFerGomez.LiterAluraChallenge.models.DatosLibro;
import org.springframework.data.jpa.repository.JpaRepository;

public class BookRepository extends JpaRepository<DatosLibro, Long> {
}
