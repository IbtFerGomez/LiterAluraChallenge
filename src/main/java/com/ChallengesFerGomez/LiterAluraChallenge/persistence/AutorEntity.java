package com.ChallengesFerGomez.LiterAluraChallenge.persistence;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "autor")
public class AutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "anio_nacimiento")
    private Integer agnoNacimiento;

    @Column(name = "anio_muerte")
    private Integer agnoMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookEntity> libros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAgnoNacimiento() {
        return agnoNacimiento;
    }

    public void setAgnoNacimiento(Integer agnoNacimiento) {
        this.agnoNacimiento = agnoNacimiento;
    }

    public Integer getAgnoMuerte() {
        return agnoMuerte;
    }

    public void setAgnoMuerte(Integer agnoMuerte) {
        this.agnoMuerte = agnoMuerte;
    }

    public List<BookEntity> getLibros() {
        return libros;
    }

    public void setLibros(List<BookEntity> libros) {
        this.libros = libros;
    }
}
