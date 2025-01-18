package com.ChallengesFerGomez.LiterAluraChallenge.repository;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {
          Optional<AutorEntity> findByNombre(String nombre);


    @Query
            ("SELECT a FROM AutorEntity a WHERE (a.agnoNacimiento <= :agno AND a.agnoMuerte IS NULL) OR (a.agnoNacimiento <= :agno AND a.agnoMuerte >= :agno)")
    List<AutorEntity> findAutoresVivosEnUnAgno(@Param("agno") int agno);
}
