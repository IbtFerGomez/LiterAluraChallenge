package com.ChallengesFerGomez.LiterAluraChallenge.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaDeNacimiento,
        @JsonAlias("death_year") String fechaDeMuerte
) {
    @Override
    public String toString() {
        return "Autor {" +
                "Nombre='" + nombre + '\'' +
                ", Año de Nacimiento='" + fechaDeNacimiento + '\'' +
                ", Año de Fallecimiento='" + fechaDeMuerte + '\'' +
                '}';
    }
}