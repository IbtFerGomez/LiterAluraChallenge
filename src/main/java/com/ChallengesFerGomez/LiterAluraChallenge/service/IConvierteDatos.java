package com.ChallengesFerGomez.LiterAluraChallenge.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
