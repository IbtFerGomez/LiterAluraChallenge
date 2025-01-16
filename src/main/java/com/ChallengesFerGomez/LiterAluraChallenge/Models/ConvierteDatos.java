package com.ChallengesFerGomez.LiterAluraChallenge.Models;

import com.ChallengesFerGomez.LiterAluraChallenge.Service.IConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return mapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir JSON a clase " + clase.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}
