package com.ChallengesFerGomez.LiterAluraChallenge.principal;

import com.ChallengesFerGomez.LiterAluraChallenge.models.Autor;
import com.ChallengesFerGomez.LiterAluraChallenge.models.Book;
import com.ChallengesFerGomez.LiterAluraChallenge.models.BookList;
import com.ChallengesFerGomez.LiterAluraChallenge.models.ConvierteDatos;
import com.ChallengesFerGomez.LiterAluraChallenge.persistence.AutorDTO;
import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookDTO;
import com.ChallengesFerGomez.LiterAluraChallenge.persistence.BookEntity;
import com.ChallengesFerGomez.LiterAluraChallenge.service.BooksService;
import com.ChallengesFerGomez.LiterAluraChallenge.service.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class LiterAluraPrincipal {


    private final ConsumoAPI apiCliente;
    private final ConvierteDatos convertidor;
    private final BooksService booksService;
    private static final Scanner scanner = new Scanner(System.in);
    ;

    @Autowired
    public LiterAluraPrincipal(ConsumoAPI apiCliente, ConvierteDatos convertidor, BooksService booksService) {
        this.apiCliente = apiCliente;
        this.convertidor = convertidor;
        this.booksService = booksService;
    }

    public void muestraElMenu() {
        while (true) {
            System.out.println("""
                    === Menú ===
                    1. Obtener todos los libros
                    2. Buscar por título o autor
                    3. Buscar por tema
                    4. Top Rates (Estadísticas y descargas)
                    5. Listar autores vivos en un año
                    6. Agregar un libro
                    7. Buscar estadísticas por idioma
                    8. Salir
                    Seleccione una opción:""");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1 -> obtenerTodosLosLibros();
                case 2 -> buscarPorTituloOAutor();
                case 3 -> buscarPorTema();
                case 4 -> menuTopRates();
                case 5 -> listarAutoresVivosEnAgno();
                case 6 -> agregarLibro();
                case 7 -> obtenerEstadisticasPorIdioma();
                case 8 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }


    // Submenú "Top Rates"
    private void menuTopRates() {
        List<Book> libros = obtenerLibros();

        if (libros == null || libros.isEmpty()) {
            System.out.println("No hay datos disponibles para mostrar estadísticas.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    
                    === Submenú: Top Rates ===
                    1. Mostrar estadísticas generales de descargas
                    2. Top 10 libros más descargados (generales)
                    3. Top 10 libros más descargados por año o rango de años
                    4. Estadísticas generales de descargas por rango de años
                    5. Regresar al menú principal
                    Seleccione una opción:""");

            switch (scanner.nextInt()) {
                case 1 -> mostrarEstadisticas(libros);
                case 2 -> mostrarTopDescargas(libros);
                case 3 -> mostrarTopDescargasPorAnio(libros);
                case 4 -> mostrarEstadisticasPorAnio(libros);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }

    //+++++++++++++++++++++Metodos De Busqueda+++++++++++++++++++++
    // Obtener y mostrar todos los libros
    private void obtenerTodosLosLibros() {
        try {
            obtenerLibros().forEach(this::imprimirLibro);
        } catch (Exception e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
    }

    // Buscar libros por título o autor
    private void buscarPorTituloOAutor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el título o nombre del autor: ");
        String termino = scanner.nextLine();

        String url = "https://gutendex.com/books/?search=" + termino;

        try {
            obtenerLibrosDesdeURL(url).forEach(this::imprimirLibro);
        } catch (Exception e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
        }
    }

    // Buscar libros por tema
    private void buscarPorTema() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el tema para buscar libros: ");
        String termino = scanner.nextLine();

        String url = "https://gutendex.com/books/?topic=" + termino;

        try {
            obtenerLibrosDesdeURL(url).forEach(this::imprimirLibro);
        } catch (Exception e) {
            System.out.println("Error al buscar libros por tema: " + e.getMessage());
        }
    }


    // Metodo para listar autores vivos en un rango de años determinado
    private void listarAutoresVivosEnAgno() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el año inicial del rango: ");
        int anioInicio = scanner.nextInt();

        System.out.print("Ingrese el año final del rango: ");
        int anioFin = scanner.nextInt();
        scanner.nextLine(); // Asegurarse de manejar la entrada correctamente

        // Construir la URL con los parámetros de rango de años
        String url = String.format("https://gutendex.com/books/?author_year_start=%d&author_year_end=%d", anioInicio, anioFin);

        try {
            // Obtener libros desde la API con los parámetros de rango
            List<Book> libros = obtenerLibrosDesdeURL(url);

            if (libros == null || libros.isEmpty()) {
                System.out.println("No se encontraron libros con autores vivos en el rango especificado.");
                return;
            }

            // Mostrar los libros encontrados con sus autores vivos
            System.out.println("\n=== Libros con autores vivos entre " + anioInicio + " y " + anioFin + " ===");
            libros.forEach(libro -> {
                String autores = libro.autor().stream()
                        .map(Autor::nombre)
                        .collect(Collectors.joining(", "));
                System.out.println("Título: " + libro.titulo() + ", Autor(es): " + autores);
            });

        } catch (Exception e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
    }


    //*****************************Estadisticas libros*********************************
    //Conteo de libro por idioma

    private void obtenerEstadisticasPorIdioma() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el idioma para las estadísticas: ");
        String idioma = scanner.nextLine();

        try {
            Map<String, Object> resultado = booksService.obtenerConteoYLibrosPorIdioma(idioma);
            System.out.println("Cantidad de libros en " + idioma + ": " + resultado.get("conteo"));

            List<BookEntity> libros = (List<BookEntity>) resultado.get("libros");
            System.out.println("Lista de libros en " + idioma + ":");
            libros.forEach(libro ->
                    System.out.println("Título: " + libro.getTitulo() + ", Tema: " + libro.getTema() +
                            ", Descargas: " + libro.getNumeroDescargas()));
        } catch (Exception e) {
            System.out.println("Error al obtener las estadísticas: " + e.getMessage());
        }
    }

    private void mostrarEstadisticas(List<Book> libros) {
        DoubleSummaryStatistics stats = libros.stream()
                .filter(l -> l.numeroDeDescargas() > 0)
                .mapToDouble(Book::numeroDeDescargas)
                .summaryStatistics();
        imprimirEstadisticas(stats);
    }

    // Mostrar Top 10 de libros más descargados
    private void mostrarTopDescargas(List<Book> libros) {
        System.out.println("\n=== Top 10 Libros Más Descargados ===");
        libros.stream()
                .filter(l -> l.numeroDeDescargas() > 0)
                .sorted(Comparator.comparing(Book::numeroDeDescargas).reversed())
                .limit(10)
                .forEach(this::imprimirLibroResumen);
    }

    // Top 10 libros más descargados por año o rango de años
    private void mostrarTopDescargasPorAnio(List<Book> libros) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el rango inicial de años: ");
        int anioInicio = scanner.nextInt();
        System.out.print("Ingrese el rango final de años: ");
        int anioFin = scanner.nextInt();

        libros.stream()
                .filter(libro -> libro.autor().stream()
                        .anyMatch(autor -> isAutorEnRango(autor, anioInicio, anioFin)))
                .sorted(Comparator.comparing(Book::numeroDeDescargas).reversed())
                .limit(10)
                .forEach(this::imprimirLibroResumen);
    }

    private void mostrarEstadisticasPorAnio(List<Book> libros) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el rango inicial de años: ");
        int anioInicio = scanner.nextInt();
        System.out.print("Ingrese el rango final de años: ");
        int anioFin = scanner.nextInt();

        DoubleSummaryStatistics stats = libros.stream()
                .filter(libro -> libro.autor().stream()
                        .anyMatch(autor -> isAutorEnRango(autor, anioInicio, anioFin)))
                .mapToDouble(Book::numeroDeDescargas)
                .summaryStatistics();
        imprimirEstadisticas(stats);
    }

    // Metodo auxiliar para verificar si un autor está en el rango
    private boolean isAutorEnRango(Autor autor, int anioInicio, int anioFin) {
        int nacimiento = autor.fechaDeNacimiento() != null ? Integer.parseInt(autor.fechaDeNacimiento()) : 0;
        int muerte = autor.fechaDeMuerte() != null ? Integer.parseInt(autor.fechaDeMuerte()) : Integer.MAX_VALUE;
        return nacimiento >= anioInicio && muerte <= anioFin;
    }

    private void imprimirEstadisticas(DoubleSummaryStatistics stats) {
        System.out.println("\n=== Estadísticas ===");
        System.out.println("Promedio de descargas: " + stats.getAverage());
        System.out.println("Máximo de descargas: " + stats.getMax());
        System.out.println("Mínimo de descargas: " + stats.getMin());
        System.out.println("Total registros procesados: " + stats.getCount());
    }

    //******************************Otener Libros*******************************

    // Obtener libros desde la URL
    private List<Book> obtenerLibrosDesdeURL(String url) {
        try {
            String respuestaJson = apiCliente.obtenerDatos(url);
            return convertidor.obtenerDatos(respuestaJson, BookList.class).getLibros();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener los libros desde la URL: " + url, e);
        }
    }
// Clase auxiliar para manejar las estadísticas con DoubleSummaryStatistics

    //*************************Imprimir libros Serializacion***********************
// Imprimir un libro con sus datos completos
    private List<Book> obtenerLibros() {
        return obtenerLibrosDesdeURL("https://gutendex.com/books/");
    }

    private void imprimirLibro(Book libro) {
        System.out.println("Título: " + libro.titulo());
        System.out.println("Autores: " + libro.autor().stream()
                .map(Autor::nombre)
                .collect(Collectors.joining(", ")));
        System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
        System.out.println("Descargas: " + libro.numeroDeDescargas());
        System.out.println("Temas: " + String.join(", ", libro.temas()));
        System.out.println();
    }

    // Imprimir solo un resumen del libro
    private void imprimirLibroResumen(Book libro) {
        System.out.println("Título: " + libro.titulo() + ", Descargas: " + libro.numeroDeDescargas());
    }

    private void agregarLibro() {

        System.out.println("=== Agregar un nuevo libro ===");

        System.out.print("Ingrese el título del libro: ");

        String titulo = scanner.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese el idioma del libro: ");
        String idioma = scanner.nextLine().trim();
        if (idioma.isEmpty()) {
            System.out.println("El idioma no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese el tema del libro: ");
        String tema = scanner.nextLine().trim();
        if (tema.isEmpty()) {
            System.out.println("El tema no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese número de descargas: ");
        Double numeroDescargas;
        try {
            numeroDescargas = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El número de descargas debe ser un valor numérico.");
            return;
        }

        System.out.print("Ingrese el nombre del autor: ");
        String autorNombre = scanner.nextLine().trim();
        if (autorNombre.isEmpty()) {
            System.out.println("El nombre del autor no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese el año de nacimiento del autor: ");
        int agnoNacimiento;
        try {
            agnoNacimiento = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El año de nacimiento debe ser un valor numérico.");
            return;
        }

        // Validar la entrada del año de muerte
        System.out.print("Ingrese el año de muerte del autor (o 0 si está vivo): ");
        Integer agnoMuerte = null;
        try {
            agnoMuerte = Integer.parseInt(scanner.nextLine());
            if (agnoMuerte == 0) {
                agnoMuerte = null; // Representar vivo como null
            }
        } catch (NumberFormatException e) {
            System.out.println("El año de muerte debe ser un valor numérico.");
            return;
        }

        // Crear el DTO usando las clases ya definidas
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitulo(titulo);
        bookDTO.setIdioma(idioma);
        bookDTO.setTema(tema);
        bookDTO.setNumeroDescargas(numeroDescargas);

        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setNombre(autorNombre);
        autorDTO.setAnioNacimiento(agnoNacimiento);
        autorDTO.setAgnoMuerte(agnoMuerte);

        bookDTO.setAutor(autorDTO);

        // Persistir el libro usando el servicio
        try {
            booksService.saveBook(bookDTO);
            System.out.println("Libro agregado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar el libro: " + e.getMessage());
        }
    }
}