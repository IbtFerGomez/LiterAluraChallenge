package com.ChallengesFerGomez.LiterAluraChallenge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookList {

    private int count;
    private String next;   // Siguiente página
    private String previous; // Página anterior

    @JsonProperty("results")
    private List<DatosLibro> libros;

    // Getters, Setters y ToString
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<DatosLibro> getLibros() {
        return libros;
    }

    public void setLibros(List<DatosLibro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "BookList{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", libros=" + libros +
                '}';
    }
}
