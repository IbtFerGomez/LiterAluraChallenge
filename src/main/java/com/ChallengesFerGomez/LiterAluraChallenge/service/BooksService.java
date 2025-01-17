package com.ChallengesFerGomez.LiterAluraChallenge.service;

import com.ChallengesFerGomez.LiterAluraChallenge.persistence.AutorEntity;
import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookDTO;
import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookEntity;
import com.ChallengesFerGomez.LiterAluraChallenge.repository.AutorRepository;
import com.ChallengesFerGomez.LiterAluraChallenge.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class BooksService {

    private final AutorRepository autorRepository;

    private final BookRepository bookRepository;

    @Autowired
    public BooksService(AutorRepository autorRepository, BookRepository bookRepository) {
        this.autorRepository = autorRepository;
        this.bookRepository = bookRepository;
    }

    public void saveBook(BookDTO bookDTO) {
        // Validar datos
        if (bookDTO.getTitulo() == null || bookDTO.getIdioma() == null || bookDTO.getAutor() == null) {
            throw new IllegalArgumentException("Datos del libro o autor faltantes.");
        }

        AutorEntity autor = autorRepository.findByNombre(bookDTO.getAutor().getNombre())
                .orElseGet(() -> {
                    AutorEntity newAutor = new AutorEntity();
                    newAutor.setNombre(bookDTO.getAutor().getNombre());
                    newAutor.setAgnoNacimiento(bookDTO.getAutor().getAnioNacimiento());
                    newAutor.setAgnoMuerte(bookDTO.getAutor().getAgnoMuerte());
                    return autorRepository.save(newAutor);
                });

        BookEntity book = new BookEntity();
        book.setTitulo(bookDTO.getTitulo());
        book.setIdioma(bookDTO.getIdioma());
        book.setNumeroDescargas(bookDTO.getNumeroDescargas());
        book.setTema(bookDTO.getTema());
        book.setAutor(autor);

        bookRepository.save(book);
    }

    public Map<String, Object> obtenerConteoYLibrosPorIdioma(String idioma) {
        long conteo = bookRepository.countByIdioma(idioma);
        List<BookEntity> librosPorIdioma = bookRepository.findByIdioma(idioma);

        return Map.of(
                "conteo", conteo,
                "libros", librosPorIdioma
        );
    }
}
