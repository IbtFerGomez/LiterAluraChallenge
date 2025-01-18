package com.ChallengesFerGomez.LiterAluraChallenge;

import com.ChallengesFerGomez.LiterAluraChallenge.models.BookList;
import com.ChallengesFerGomez.LiterAluraChallenge.models.ConvierteDatos;
import com.ChallengesFerGomez.LiterAluraChallenge.principal.LiterAluraPrincipal;
import com.ChallengesFerGomez.LiterAluraChallenge.service.BooksService;
import com.ChallengesFerGomez.LiterAluraChallenge.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {

	private final LiterAluraPrincipal principal;
	private final ConsumoAPI apiCliente;
	private final ConvierteDatos convertidor;
	private final BooksService booksService;

	@Autowired
	public LiterAluraChallengeApplication(LiterAluraPrincipal principal, ConsumoAPI apiCliente, ConvierteDatos convertidor, BooksService booksService) {
		this.principal = principal;
		this.apiCliente = apiCliente;
		this.convertidor = convertidor;
		this.booksService = booksService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Persistir los datos iniciales de la API al iniciar la aplicación
		String url = "https://gutendex.com/books/";
		try {
			String respuestaJson = apiCliente.obtenerDatos(url);
			BookList bookList = convertidor.obtenerDatos(respuestaJson, BookList.class);
			booksService.saveBooksFromAPI(bookList.getLibros());
			System.out.println("Los datos iniciales de la API fueron guardados correctamente en la base de datos.");
		} catch (Exception e) {
			System.out.println("Error al guardar datos iniciales de la API: " + e.getMessage());
		}

		// Iniciar el menú interactivo
		principal.muestraElMenu();
	}
}
