package com.ChallengesFerGomez.LiterAluraChallenge.contorllers;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookDTO;
import com.ChallengesFerGomez.LiterAluraChallenge.service.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookShelfController {
    private final BooksService booksService;

    public BookShelfController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping
    public ResponseEntity<String> saveBook(@RequestBody BookDTO bookDTO) {
        try {
            booksService.saveBook(bookDTO);
            return new ResponseEntity<>("Libro guardado correctamente", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el libro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/conteo-por-idioma/{idioma}")
    public ResponseEntity<Map<String, Object>> obtenerConteoYLibrosPorIdioma(@PathVariable String idioma) {
        try {
            Map<String, Object> resultado = booksService.obtenerConteoYLibrosPorIdioma(idioma);
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "No se pudo calcular las estadísticas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

