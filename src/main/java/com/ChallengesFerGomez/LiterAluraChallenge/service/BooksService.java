package com.ChallengesFerGomez.LiterAluraChallenge.service;

import com.ChallengesFerGomez.LiterAluraChallenge.models.Book;
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

    public void saveBooksFromAPI(List<Book> booksFromAPI) {
        booksFromAPI.forEach(book -> {
            try {
                AutorEntity autor = autorRepository.findByNombre(book.autor().get(0).nombre()) // Asume un solo autor
                        .orElseGet(() -> {
                            AutorEntity newAutor = new AutorEntity();
                            newAutor.setNombre(book.autor().get(0).nombre());
                            if (book.autor().get(0).fechaDeNacimiento() != null)
                                newAutor.setAgnoNacimiento(Integer.parseInt(book.autor().get(0).fechaDeNacimiento()));
                            if (book.autor().get(0).fechaDeMuerte() != null)
                                newAutor.setAgnoMuerte(Integer.parseInt(book.autor().get(0).fechaDeMuerte()));
                            return autorRepository.save(newAutor);
                        });

                BookEntity bookEntity = new BookEntity();
                bookEntity.setTitulo(book.titulo());
                bookEntity.setIdioma(book.idiomas().isEmpty() ? "Desconocido" : book.idiomas().get(0)); // Primer idioma
                bookEntity.setNumeroDescargas(book.numeroDeDescargas());
                bookEntity.setTema(book.temas() != null && !book.temas().isEmpty() ? book.temas().get(0) : "General"); // Primer tema
                bookEntity.setAutor(autor);

                bookRepository.save(bookEntity);
            } catch (Exception e) {
                System.err.println("Error al guardar el libro: " + book.titulo() + ". Causa: " + e.getMessage());
            }
        });}

    public List<BookEntity> findBooksBySpanishOrEnglish() {
        return bookRepository.findBooksBySpanishOrEnglish();
    }

    public List<BookEntity> findBooksByTema(String tema) {
        return bookRepository.findByTema(tema);
    }

    public long countBooksByIdioma(String idioma) {
        return bookRepository.countByIdioma(idioma);
    }
    // BooksService
    public Map<String, Long> getBookCountByIdioma() {
        List<BookEntity> allBooks = bookRepository.findAll();

        return allBooks.stream()
                .collect(Collectors.groupingBy(BookEntity::getIdioma, Collectors.counting()));
    }

    public List<AutorEntity> listarAutoresVivosEnUnAgno(int agno ) {
        if (agno <= 0) {
            throw new IllegalArgumentException("El año ingresado es inválido.");
        }
        return autorRepository.findAutoresVivosEnUnAgno(agno);
    }
}
