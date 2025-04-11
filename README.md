# 📊 Event Dashboard - Spring Boot REST API

Aplicación web desarrollada con Spring Boot para capturar, gestionar y visualizar eventos en tiempo real. Permite registrar eventos con distintos tipos y mensajes, almacenarlos en MongoDB, y visualizar el historial de eventos a través de una interfaz web utilizando Thymeleaf.

## 🚀 Características

- Captura de eventos con diferentes tipos (INFO, ERROR, WARNING, etc.).
- Almacenamiento de eventos en MongoDB.
- Visualización de eventos en un panel con Thymeleaf.
- API REST para gestionar eventos (crear y listar).
- Sistema de logs con timestamp para cada evento.
- Integración sencilla y rápida con Docker.

---  

## 🧱 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data MongoDB
- Thymeleaf (para el frontend)
- Docker (para la implementación)
- Maven
- Springdoc OpenAPI (documentación de la API)
- API REST

---

## 📦 Instalación

1. Clona el repositorio:

```bash
git clone https://github.com/javilesaca/event-dashboard.git
cd event-dashboard
```
2. Construye el proyecto:
   
```bash
./mvnw clean install
```
3. Ejecuta la aplicación:

```bash
./mvnw spring-boot:run
```
4. Accede a la API en: http://localhost:8080/api/events

La aplicación también está accesible en el navegador en: http://localhost:8080

---

 ## 📘 Endpoints principales

| Método |	Endpoint | Descripción |
|:-------|:----------|:------------|
| POST |	/api/events |	Crear un nuevo evento |
| GET |	/api/events	| 	Listar todos los eventos registrados|

Ejemplo de creación de evento (POST)

Cuerpo de la solicitud (JSON):
```bash
{
  "type": "INFO",
  "message": "La aplicación ha arrancado",
  "source": "System"
}
```
Respuesta de la solicitud (JSON)
```bash
{
  "type": "INFO",
  "message": "La aplicación ha arrancado",
  "source": "System",
  "timestamp": "2024-02-11T16:47:48.461749471",
  "id": "67f92b9483c2202e94e514a4"
}
```
Añade manualmente todos los eventos que desees respetando la estructura JSON

---

## 🧑‍💻 Frontend - Visualización de eventos

Una vez que el backend esté en marcha, accede al panel visual para ver los eventos capturados en el navegador. Se mostrarán en una tabla, ordenados por timestamp.

---

## 📊 Acceso al Frontend

1. Abre tu navegador y ve a http://localhost:8080.

2. Visualiza los eventos registrados en tiempo real.

---

## 🧪 Pruebas 

En desarrollo. Se puede añadir pruebas unitarias con JUnit 5 y pruebas de integración con Spring Boot Test para validar los endpoints.

---

## 🔧 Docker

Este proyecto puede ser ejecutado en Docker para una fácil implementación. Utiliza el archivo `docker-compose.yml` para levantar la base de datos MongoDB.
```bash
docker-compose up
```

---

## 📘 Documentación de la API

La documentación interactiva de la API está disponible a través de Swagger:

Accede a: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Aquí puedes ver todos los endpoints de la API, realizar pruebas y ver ejemplos de solicitudes y respuestas.

---

## ✍️ Autor

Javier Lesaca Medina

---

## ⭐ Licencia
Este proyecto está bajo la licencia MIT.


