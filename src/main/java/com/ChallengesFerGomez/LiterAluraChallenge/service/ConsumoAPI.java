package com.ChallengesFerGomez.LiterAluraChallenge.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConsumoAPI {
    private final HttpClient httpClient;

    // Constructor para inicializar el HttpClient
    public ConsumoAPI() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // Metodo para obtener datos desde una URL
    public String obtenerDatos(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET() // Configura el método HTTP como GET
                .build();

        try {
            HttpResponse<String> response = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());

// Verifica el estado de la respuesta
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body(); // Retorna el cuerpo en formato JSON si es exitoso
            } else {
                throw new RuntimeException("Error en la respuesta: Código " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al realizar la solicitud a la API: " + e.getMessage(), e);
        }
    }
}


/*public class ConsumoAPI {
public String obtenerDatos(String url){
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
.uri(URI.create(url))
.build();
HttpResponse<String> response = null;
try {
response = client
.send(request, HttpResponse.BodyHandlers.ofString());
} catch (IOException e) {
throw new RuntimeException(e);
} catch (InterruptedException e) {
throw new RuntimeException(e);
}
String json = response.body();
return json;
}
}*/
