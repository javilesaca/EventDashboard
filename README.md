# 📊 EventDashboard — Spring Boot REST API

Microservicio para capturar, consultar y visualizar eventos de aplicación en tiempo real.
Backend con **Spring Boot 3 + MongoDB**, panel web con **Thymeleaf** y eventos en vivo vía **Server-Sent Events (SSE)**.

## 🚀 Características

- Registro de eventos con tipo, mensaje, origen y timestamp (`POST /api/events` → `201 Created` + cabecera `Location`).
- Validación de entrada: `400` con errores por campo en lugar de `500`.
- Búsqueda con filtros (`type`, `source`, rango `from`/`to`) y paginación.
- Evento individual (`GET /api/events/{id}`, `404` si no existe).
- Flujo en tiempo real (`GET /api/events/stream`, SSE): cada evento creado se emite a todos los suscriptores.
- Panel web (`/`) con filtros, contadores por tipo y actualización automática sin recargar.
- Documentación interactiva con Swagger UI.
- Tests unitarios y de controlador (`./mvnw test`, 9 tests, sin necesidad de MongoDB).
- Perfil `demo` que carga 40 eventos de ejemplo en una base vacía.

## 🧱 Tecnologías

Java 17 · Spring Boot 3.4 · Spring Data MongoDB · Thymeleaf · Springdoc OpenAPI · JUnit 5 + Mockito · Docker Compose (MongoDB 6)

## 📦 Puesta en marcha

```bash
docker compose up -d        # MongoDB en localhost:27017
./mvnw spring-boot:run      # API en http://localhost:8080
```

Con datos de ejemplo:

```bash
SPRING_PROFILES_ACTIVE=demo ./mvnw spring-boot:run
```

- Panel: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## 📘 Endpoints principales

| Método | Endpoint | Descripción |
|:-------|:---------|:------------|
| POST | `/api/events` | Crear un evento → `201` + `Location` |
| GET | `/api/events?type=&source=&from=&to=&page=&size=` | Buscar con filtros y paginación |
| GET | `/api/events/{id}` | Obtener un evento (`404` si no existe) |
| GET | `/api/events/stream` | Suscripción SSE a eventos en tiempo real |
| GET | `/` | Panel web con filtros y contadores |

Ejemplo de creación:

```bash
curl -X POST http://localhost:8080/api/events \
  -H 'Content-Type: application/json' \
  -d '{"type":"INFO","message":"La aplicación ha arrancado","source":"system"}'
```

## 🧪 Tests

```bash
./mvnw test
```

Unitarios del servicio (Mockito) + slice del controlador (`@WebMvcTest` con servicio mockeado).
No requieren MongoDB. La prueba de integración contra Mongo real está pendiente de Testcontainers.

## ✍️ Autor

Javier Lesaca Medina — MIT
