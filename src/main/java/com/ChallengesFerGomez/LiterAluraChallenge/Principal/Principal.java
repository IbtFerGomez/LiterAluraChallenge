package com.ChallengesFerGomez.LiterAluraChallenge.Principal;

import com.ChallengesFerGomez.LiterAluraChallenge.Models.ConvierteDatos;
import com.ChallengesFerGomez.LiterAluraChallenge.Models.DatosLibro;
import com.ChallengesFerGomez.LiterAluraChallenge.Models.LibrosListaResponse;
import com.ChallengesFerGomez.LiterAluraChallenge.Service.consumoAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

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
            System.out.println("\n=== Menú ===");
            System.out.println("1. Buscar libros (por término general, autor o título)");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Evitar problemas con la entrada

            switch (opcion) {
                case 1 -> buscarLibrosPorTermino();
                case 2 -> {
                    System.out.println("Saliendo del programa...");
                    return;
                }
                default -> System.out.println("Opción no válida, intente nuevamente.");
            }
        }
    }

    // Metodo para buscar libros según un término ingresado por el usuario
    private void buscarLibrosPorTermino() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el término de búsqueda (puede ser título o autor): ");
        String termino = scanner.nextLine();

        // Codificar el término para evitar problemas con caracteres en la URL
        String terminoCodificado = URLEncoder.encode(termino, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books/?search=" + terminoCodificado;

        procesarYMostrarLibros(url);
    }

    // Metodo común para procesar los resultados y mostrar los libros obtenidos
    private void procesarYMostrarLibros(String url) {
        try {
            // Obtener el JSON desde la API
            String respuestaJson = apiCliente.obtenerDatos(url);

            // Convertir JSON a LibrosListaResponse usando el convertidor genérico
            LibrosListaResponse listaLibros = convertidor.obtenerDatos(respuestaJson, LibrosListaResponse.class);

            // Mostrar los resultados
            List<DatosLibro> libros = listaLibros.getLibros();
            if (libros == null || libros.isEmpty()) {
                System.out.println("No se encontraron libros para la búsqueda proporcionada.");
            } else {
                System.out.println("\nResultados de la búsqueda:");
                libros.forEach(System.out::println); // Imprime cada libro utilizando el método toString()
            }
        } catch (RuntimeException e) {
            System.out.println("Hubo un error al procesar la solicitud: " + e.getMessage());
        }
    }
}
