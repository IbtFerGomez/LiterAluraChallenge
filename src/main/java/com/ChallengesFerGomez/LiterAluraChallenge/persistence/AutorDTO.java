package com.ChallengesFerGomez.LiterAluraChallenge.persistence;



public class AutorDTO {
    private String nombre;
    private Integer agnoNacimiento;
    private Integer agnoMuerte;

// Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return agnoNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.agnoNacimiento = anioNacimiento;
    }

    public Integer getAgnoMuerte() {
        return agnoMuerte;
    }

    public void setAgnoMuerte(Integer agnoMuerte) {
        this.agnoMuerte = agnoMuerte;
    }
}
