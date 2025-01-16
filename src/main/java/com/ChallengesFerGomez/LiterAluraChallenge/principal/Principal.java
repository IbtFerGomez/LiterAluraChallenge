package com.ChallengesFerGomez.LiterAluraChallenge.principal;

import com.ChallengesFerGomez.LiterAluraChallenge.models.BookList;
import com.ChallengesFerGomez.LiterAluraChallenge.models.ConvierteDatos;
import com.ChallengesFerGomez.LiterAluraChallenge.models.DatosAutor;
import com.ChallengesFerGomez.LiterAluraChallenge.models.DatosLibro;
import com.ChallengesFerGomez.LiterAluraChallenge.service.consumoAPI;
import java.util.DoubleSummaryStatistics;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final consumoAPI apiCliente;
    private final ConvierteDatos convertidor;

    // Constructor para inicializar las dependencias
    public Principal() {
        this.apiCliente = new consumoAPI();
        this.convertidor = new ConvierteDatos();
    }

    public void muestraElMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    
                    === Menú ===
                    1. Obtener todos los libros
                    2. Buscar por título o autor
                    3. Buscar por tema
                    4. Top Rates (Estadísticas y descargas)
                    5. Listar autores vivos en un año
                    6. Salir
                    Seleccione una opción:""");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Evitar problemas con la entrada

            switch (opcion) {
                case 1 -> obtenerTodosLosLibros();
                case 2 -> buscarPorTituloOAutor();
                case 3 -> buscarPorTema();
                case 4 -> menuTopRates();
                case 5 -> listarAutoresVivosEnAgno();
                case 6 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }

    // Submenú "Top Rates"
    private void menuTopRates() {
        List<DatosLibro> libros = obtenerLibros();

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
    // Metodo para listar autores vivos en un determinado año
    private void listarAutoresVivosEnAgno() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese un año para listar los autores vivos: ");
        int anio = scanner.nextInt();
        scanner.nextLine();} // Manejando entrada adicional

 //*****************************Estadisticas libros*********************************
    // Mostrar estadísticas generales de descargas
    private void mostrarEstadisticas(List<DatosLibro> libros) {
        libros.stream()
                .filter(l -> l.numeroDeDescargas() > 0)
                .mapToDouble(DatosLibro::numeroDeDescargas)
                .summaryStatistics();
        new DoubleSummaryStatisticsHandler().accept((DoubleSummaryStatistics) libros);
    }

    // Mostrar Top 10 de libros más descargados
    private void mostrarTopDescargas(List<DatosLibro> libros) {
        System.out.println("\n=== Top 10 Libros Más Descargados ===");
        libros.stream()
                .filter(l -> l.numeroDeDescargas() > 0)
                .sorted(Comparator.comparing(DatosLibro::numeroDeDescargas).reversed())
                .limit(10)
                .forEach(this::imprimirLibroResumen);
    }

    // Top 10 libros más descargados por año o rango de años
    private void mostrarTopDescargasPorAnio(List<DatosLibro> libros) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el rango inicial de años: ");
        int anioInicio = scanner.nextInt();
        System.out.print("Ingrese el rango final de años: ");
        int anioFin = scanner.nextInt();

        libros.stream()
                .filter(libro -> libro.autor().stream()
                        .anyMatch(autor -> isAutorEnRango(autor, anioInicio, anioFin)))
                .sorted(Comparator.comparing(DatosLibro::numeroDeDescargas).reversed())
                .limit(10)
                .forEach(this::imprimirLibroResumen);
    }

    // Estadísticas por rango de años
    private void mostrarEstadisticasPorAnio(List<DatosLibro> libros) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nIngrese el rango inicial de años: ");
        int anioInicio = scanner.nextInt();
        System.out.print("Ingrese el rango final de años: ");
        int anioFin = scanner.nextInt();

        DoubleSummaryStatistics estadisticas = libros.stream()
                .filter(libro -> libro.autor().stream()
                        .anyMatch(autor -> isAutorEnRango(autor, anioInicio, anioFin)))
                .mapToDouble(DatosLibro::numeroDeDescargas)
                .summaryStatistics();

        new DoubleSummaryStatisticsHandler().accept(estadisticas);
    }

    // Metodo auxiliar para verificar si un autor está en el rango
    private boolean isAutorEnRango(DatosAutor autor, int anioInicio, int anioFin) {
        int nacimiento = autor.fechaDeNacimiento() != null ? Integer.parseInt(autor.fechaDeNacimiento()) : 0;
        int muerte = autor.fechaDeMuerte() != null ? Integer.parseInt(autor.fechaDeMuerte()) : Integer.MAX_VALUE;
        return nacimiento >= anioInicio && muerte <= anioFin;
    }

//******************************Otener Libros*******************************
    // Obtener libros desde la URL
    private List<DatosLibro> obtenerLibros() {
        return obtenerLibrosDesdeURL("https://gutendex.com/books/");
    }
    static class DoubleSummaryStatisticsHandler {
        void accept(DoubleSummaryStatistics stats) {
            System.out.println("\n=== Estadísticas ===");
            System.out.println("Promedio de descargas: " + stats.getAverage());
            System.out.println("Máximo de descargas: " + stats.getMax());
            System.out.println("Mínimo de descargas: " + stats.getMin());
            System.out.println("Total registros procesados: " + stats.getCount());
        }
    }
    private List<DatosLibro> obtenerLibrosDesdeURL(String url) {
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
    private void imprimirLibro(DatosLibro libro) {
        System.out.println("Título: " + libro.titulo());
        System.out.println("Autores: " + libro.autor().stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", ")));
        System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
        System.out.println("Descargas: " + libro.numeroDeDescargas());
        System.out.println("Temas: " + String.join(", ", libro.temas()));
        System.out.println();
    }

    // Imprimir solo un resumen del libro
    private void imprimirLibroResumen(DatosLibro libro) {
        System.out.println("Título: " + libro.titulo() + ", Descargas: " + libro.numeroDeDescargas());
    }
}

