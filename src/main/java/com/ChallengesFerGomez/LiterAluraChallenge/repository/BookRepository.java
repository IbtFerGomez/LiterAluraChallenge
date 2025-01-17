package com.ChallengesFerGomez.LiterAluraChallenge.repository;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByIdioma(String idioma);
    long countByIdioma(String idioma);
}
