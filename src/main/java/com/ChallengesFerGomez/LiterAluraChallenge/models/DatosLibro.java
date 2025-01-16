package com.ChallengesFerGomez.LiterAluraChallenge.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("id") int id,//<number of Project Gutenberg ID>,
        @JsonAlias("title") String titulo,//<string>,
        @JsonProperty("authors") List<DatosAutor> autor,//<array of Persons>
        @JsonProperty("languages") List<String> idiomas,
        @JsonProperty("download_count") Double numeroDeDescargas,
        @JsonAlias("subjects") List<String> temas//<array of strings>,
) {


    @Override
    public String toString() {
        return "Libro {" +
                "Título='" + titulo + '\'' +
                ", AutorEntity(es)=" + autor +
                ", Idiomas=" + idiomas +
                ", Descargas=" + numeroDeDescargas +
                ", Temas=" + temas +
                '}';
    }
}

