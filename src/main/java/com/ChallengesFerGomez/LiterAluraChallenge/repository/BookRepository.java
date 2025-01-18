package com.ChallengesFerGomez.LiterAluraChallenge.repository;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByIdioma(String idioma);
    long countByIdioma(String idioma);
    List<BookEntity> findByTema(String tema);

    @Query("SELECT b FROM BookEntity b WHERE b.idioma = 'es' OR b.idioma = 'en'")
    List<BookEntity> findBooksBySpanishOrEnglish();
}

