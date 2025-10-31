# Videogames API

A simple Spring Boot application exposing a REST API to manage videogames, genres, and studios. It also includes a minimal server-side UI (Thymeleaf) but this document focuses on the API.

- Tech stack: Spring Boot 3, Spring Web, Spring Data JPA, Lombok, MapStruct, MySQL
- Java: 21

## Quick start

Prerequisites
- JDK 21
- MySQL running and configured in `src/main/resources/application.properties`

Build and run
```bash
./mvnw clean package
./mvnw spring-boot:run
```

Default base URL
- http://localhost:8080

API base path
- http://localhost:8080/api

Content type
- Requests: `application/json`
- Responses: `application/json`

Auth
- None (open API for demo purposes)

---

## Data transfer objects (DTOs)

VideogameDTO
```json
{
  "id": 1,
  "name": "The Legend of Something",
  "description": "Short game description (5..100 chars)",
  "imageUrl": "cover-123.png",
  "price": 59.99,
  "stock": 100,
  "releaseDate": "2024-10-01T00:00:00.000+00:00",
  "inactive": false,
  "studioId": 10,
  "studioName": "Nintendo",
  "genreId": 5,
  "genreName": "Adventure"
}
```

StudioDTO
```json
{
  "id": 10,
  "name": "Nintendo",
  "inactive": false
}
```

GenreDTO
```json
{
  "id": 5,
  "name": "Adventure",
  "inactive": false
}
```

Validation rules (high level)
- Videogame
  - name: not empty
  - description: 5..100 chars
  - price: 0..1000
  - stock: 0..10000
  - releaseDate: past or present
  - studioId and genreId must reference existing Studio and Genre
- Studio/Genre
  - name: not empty

Note: Validation is done primarily at the entity level. For REST requests, send valid data and expect 400/500 on invalid persistence (a dedicated REST validation handler can be added as an improvement).

---

## Endpoints

### Videogames

- GET /api/videogames
  - Description: List all active videogames
  - Response: 200 OK, array of VideogameDTO
  - Example
    ```bash
    curl -s http://localhost:8080/api/videogames | jq
    ```

- GET /api/videogames/{id}
  - Description: Get one active videogame by id
  - Response: 200 OK (VideogameDTO) or 404 Not Found
  - Example
    ```bash
    curl -s http://localhost:8080/api/videogames/1 | jq
    ```

- GET /api/videogames/search?query={text}
  - Description: Full-text search by name (case-insensitive) over active videogames
  - Response: 200 OK, array of VideogameDTO
  - Example
    ```bash
    curl -s "http://localhost:8080/api/videogames/search?query=doom" | jq
    ```

- POST /api/videogames
  - Description: Create a new videogame
  - Request body: VideogameDTO (studioId and genreId required)
  - Response: 201 Created (VideogameDTO)
  - Example
    ```bash
    curl -s -X POST http://localhost:8080/api/videogames \
      -H 'Content-Type: application/json' \
      -d '{
            "name":"My New Game",
            "description":"A great game",
            "price":49.99,
            "stock":10,
            "releaseDate":"2024-10-01T00:00:00.000+00:00",
            "studioId":1,
            "genreId":1
          }' | jq
    ```

- PUT /api/videogames/{id}
  - Description: Update an existing videogame (full replace semantics)
  - Request body: VideogameDTO
  - Response: 200 OK (VideogameDTO) or 404 Not Found
  - Example
    ```bash
    curl -s -X PUT http://localhost:8080/api/videogames/1 \
      -H 'Content-Type: application/json' \
      -d '{
            "name":"Updated Name",
            "description":"Updated description",
            "price":59.99,
            "stock":7,
            "releaseDate":"2024-10-01T00:00:00.000+00:00",
            "studioId":1,
            "genreId":1
          }' | jq
    ```

- DELETE /api/videogames/{id}
  - Description: Soft-delete a videogame (marks as inactive)
  - Response: 204 No Content or 404 Not Found
  - Example
    ```bash
    curl -i -X DELETE http://localhost:8080/api/videogames/1
    ```

### Genres

- GET /api/genres
- GET /api/genres/{id}
- POST /api/genres
- PUT /api/genres/{id}
- DELETE /api/genres/{id}

Examples
```bash
curl -s http://localhost:8080/api/genres | jq
curl -s http://localhost:8080/api/genres/1 | jq
curl -s -X POST http://localhost:8080/api/genres -H 'Content-Type: application/json' -d '{"name":"Action"}' | jq
curl -s -X PUT  http://localhost:8080/api/genres/1 -H 'Content-Type: application/json' -d '{"name":"Action & Adventure"}' | jq
curl -i -X DELETE http://localhost:8080/api/genres/1
```

### Studios

- GET /api/studios
- GET /api/studios/{id}
- POST /api/studios
- PUT /api/studios/{id}
- DELETE /api/studios/{id}

Examples
```bash
curl -s http://localhost:8080/api/studios | jq
curl -s http://localhost:8080/api/studios/1 | jq
curl -s -X POST http://localhost:8080/api/studios -H 'Content-Type: application/json' -d '{"name":"Nintendo"}' | jq
curl -s -X PUT  http://localhost:8080/api/studios/1 -H 'Content-Type: application/json' -d '{"name":"Nintendo Co."}' | jq
curl -i -X DELETE http://localhost:8080/api/studios/1
```

---

## Error handling

Current behavior
- 404 Not Found when a resource is missing (e.g., `ResourceNotFoundException`).
- 500 Internal Server Error for unexpected exceptions.

Note: The bundled `GlobalExceptionHandler` currently renders an HTML error view (Thymeleaf). For REST clients, a better approach is a dedicated `@RestControllerAdvice` that returns JSON error payloads, e.g.
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Videogame not found (id=123)",
  "path": "/api/videogames/123",
  "timestamp": "2025-10-25T23:10:00Z"
}
```