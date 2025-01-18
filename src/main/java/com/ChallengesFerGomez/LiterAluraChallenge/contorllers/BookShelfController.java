package com.ChallengesFerGomez.LiterAluraChallenge.contorllers;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookDTO;
import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookEntity;
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
    // Buscar libros en español e inglés
    @GetMapping("/idiomas/es-en")
    public ResponseEntity<List<BookEntity>> getBooksBySpanishOrEnglish() {
        List<BookEntity> libros = booksService.findBooksBySpanishOrEnglish();
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Buscar libros por tema
    @GetMapping("/temas/{tema}")
    public ResponseEntity<List<BookEntity>> getBooksByTema(@PathVariable String tema) {
        List<BookEntity> libros = booksService.findBooksByTema(tema);
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Conteo de libros por idioma
    @GetMapping("/conteo-idiomas/{idioma}")
    public ResponseEntity<Long> getBookCountByIdioma(@PathVariable String idioma) {
        long count = booksService.countBooksByIdioma(idioma);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}

