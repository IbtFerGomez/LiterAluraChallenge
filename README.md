README
## README - Proyecto LiterAluraChallenge
### Descripción
**LiterAluraChallenge** es una aplicación desarrollada en **Java** con el framework **Spring Boot**, diseñada con el propósito de administrar y explorar una colección de libros utilizando datos de la API de Project Gutenberg: **[Gutendex]()**. La aplicación permite realizar diferentes tipos de búsqueda, estadísticas de libros y gestión de autores/obras literarias almacenadas en una base de datos.
El proyecto utiliza una arquitectura orientada a servicios para interactuar con la API, almacenar datos en una base de datos PostgreSQL y proporcionar una interfaz interactiva tanto en consola como vía endpoints REST.

### Tecnologías Utilizadas
- **Java 23** con soporte para `--enable-preview`.
- **Spring Boot 3.4.1**.
    - Spring Data JPA.
    - Spring Boot Starter Web.

- **PostgreSQL** como gestor de base de datos.
- **Maven** como sistema de construcción y dependencia.
- **Jackson** para procesar datos JSON al interactuar con la API.

### Funcionalidades Principales
#### Menú en Consola
La aplicación ofrece una interfaz desde la consola con las siguientes opciones:
1. **Obtener todos los libros**: Muestra los libros almacenados en la base de datos.
2. **Buscar por título o autor**: Permite buscar un libro en la API o base de datos mediante coincidencias con el título o el autor.
3. **Buscar por tema**: Encuentra libros relacionados con temas específicos.
4. **Top descargados**: Muestra estadísticas de descargas (top general y por rangos de años).
5. **Listar autores vivos en un año**: Obtiene una lista de autores vivos en un año o rango de años específicos.
6. **Agregar un libro**: Permite añadir un libro nuevo manualmente en la base de datos.
7. **Buscar estadísticas por idioma**: Ver estadísticas de libros disponibles en un idioma específico (conteo y detalles).
8. **Obtener estadísticas globales**: Analiza y presenta estadísticas de todos los libros almacenados por idioma.
9. **Cargar libros desde la API**: Descarga libros directamente desde la API, los transforma y los almacena en la base de datos PostgreSQL.
10. **Salir**: Cierra la aplicación.

#### Endpoints REST (API REST)
La aplicación expone un control de recursos de libros y funcionalidades relacionadas. Los endpoints disponibles incluyen:
1. `POST /books`: Agregar un libro a la base de datos.
2. `GET /books/conteo-por-idioma/{idioma}`: Obtiene estadísticas de libros en un idioma específico (conteo y lista).
3. `GET /books/idiomas/es-en`: Devuelve una lista de libros en español e inglés.
4. `GET /books/temas/{tema}`: Devuelve libros relacionados con un tema específico.
5. `GET /books/conteo-idiomas/{idioma}`: Muestra el número total de libros en un idioma.

#### Base de Datos
El esquema incluye dos tablas principales:
1. **`libro`**: Contiene los datos de los libros almacenados.
2. **`autor`**: Contiene información sobre los autores.

Relación: Cada libro está relacionado con un autor mediante una clave foránea.
### Configuración del Proyecto
#### Prerrequisitos
- **Java Development Kit (JDK)** versión 23.
- **PostgreSQL** instalado y configurado.
- **Maven** para manejar dependencias y construcción del proyecto.

#### Instrucciones para Ejecutar
1. **Clonar el proyecto**:
   git clone [URL_DEL_REPOSITORIO]
   cd LiterAluraChallenge

2. **Configurar la base de datos en `application.properties`**: Edita el archivo ubicado en `src/main/resources/application.properties`, actualizando los valores con los datos de tu entorno:
   spring.datasource.url=jdbc:postgresql://localhost:5432/LiterAluraChallenge_DATABASE
   spring.datasource.username=literalurauser
   spring.datasource.password=Lufe3120$
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.driver-class-name=org.postgresql.Driver

3. **Construir y ejecutar la aplicación**:
    - Compila y ejecuta el proyecto con Maven:
         mvn clean install
     mvn spring-boot:run

4. **Acceso a la Consola**: Una vez iniciado, navega por el menú interactivo en la consola para realizar las diferentes funcionalidades.

### Futuras Mejoras
- Implementar autenticación y autorización para el API REST.
- Añadir más funcionalidades de búsqueda avanzada.
- Optimizar consultas a la base de datos mejorando índices y caché.

### Licencia
Este proyecto está desarrollado para prácticas  y no tiene licencia comercial.
**¡Gracias por explorar LiterAluraChallenge!**
Desarrolaldo por Fer Gomez
