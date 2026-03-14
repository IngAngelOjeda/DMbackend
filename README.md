# DMBackend

**DMBackend** es una API REST desarrollada con **Spring Boot** para el control de conductores de plataforma.  
Está diseñada para integrarse con **Keycloak** como proveedor de autenticación y autorización, y utiliza **PostgreSQL** como base de datos principal.

---

## Tecnologías principales

- **Java 21+**
- **Spring Boot 3+**
    - Spring Web
    - Spring Security (OAuth2 Resource Server)
    - Spring Data JPA / Hibernate
- **Keycloak** para autenticación y gestión de usuarios
- **PostgreSQL** como motor de base de datos
- **SpringDoc OpenAPI / Swagger UI** para documentación
- **Maven** como gestor de dependencias

---

## Arquitectura

El proyecto sigue una estructura basada en **Clean Architecture**, separando las capas de dominio, aplicación e infraestructura.  
Esto facilita la escalabilidad, mantenibilidad y testeo del sistema.

## Documentación (Swagger)

Una vez levantada la aplicación, la documentación estará disponible en

http://localhost:8088/api/swagger-ui/index.html


