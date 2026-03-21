# CLAUDE.md

## Contexto del proyecto
Este proyecto es una API REST desarrollada en Spring Boot 3.5.1.

El sistema permite que un usuario se registre en la plataforma y gestione la operación de sus vehículos y conductores.

La API está enfocada en registrar:
- usuarios propietarios del sistema
- vehículos
- conductores
- plataformas de trabajo (por ejemplo Uber, Bolt)
- sesiones de uso de vehículos por parte de conductores

El objetivo principal es llevar trazabilidad operativa y obtener métricas sobre el uso de los vehículos.

Actualmente NO se manejan:
- pagos
- cobros
- facturación
- alquileres formales
- contratos financieros

---

## Objetivo funcional actual
Un usuario se registra en la API.

Luego puede:
1. registrar sus vehículos
2. registrar sus conductores
3. registrar las plataformas en las que trabajan esos conductores
4. registrar sesiones de uso de vehículo

Cada sesión representa un período en el que un conductor utiliza un vehículo.

Al iniciar una sesión se registran datos como:
- conductor
- vehículo
- plataforma
- fecha/hora de inicio
- kilometraje inicial
- combustible inicial

Al finalizar una sesión se registran datos como:
- fecha/hora de fin
- kilometraje final
- combustible final

A partir de esos datos se podrán calcular métricas operativas.

---

## Stack tecnológico
- Java 21+
- Spring Boot 3.5.1
- Spring Web
- Spring Data JPA / Hibernate
- Jakarta Validation
- Base de datos relacional (PostgreSQL o similar)
- Maven o Gradle

---

## Dominio principal

### Entidades principales
- User
- Vehicle
- Driver
- Platform
- Session

### Relaciones esperadas
- Un User tiene muchos Vehicles
- Un User tiene muchos Drivers
- Un User tiene muchas Platforms
- Un Driver puede trabajar en una o varias Platforms
- Un Driver puede registrar muchas Sessions
- Un Vehicle puede participar en muchas Sessions a lo largo del tiempo
- Una Session pertenece a un User, un Driver, un Vehicle y una Platform

---

## Descripción de entidades

### User
Representa a la persona dueña de la operación dentro del sistema.

Responsabilidades:
- autenticarse o registrarse
- administrar sus propios vehículos
- administrar sus propios conductores
- administrar sus propias plataformas
- consultar sesiones y métricas de su operación

### Vehicle
Representa un vehículo propiedad o administrado por el usuario.

Posibles atributos:
- id
- brand
- model
- plate
- year
- active
- createdAt
- updatedAt

Reglas:
- la placa debe ser única al menos dentro del contexto del usuario, según la decisión de negocio
- un vehículo puede tener muchas sesiones a lo largo del tiempo

### Driver
Representa a una persona que conduce un vehículo del usuario.

Posibles atributos:
- id
- firstName
- lastName
- documentNumber
- phone
- active
- createdAt
- updatedAt

Reglas:
- un conductor pertenece a un usuario
- un conductor puede estar asociado a una o varias plataformas
- un conductor puede tener muchas sesiones

### Platform
Representa la plataforma en la que trabaja el conductor.

Ejemplos:
- Uber
- Bolt
- InDrive

Posibles atributos:
- id
- name
- description
- active
- createdAt
- updatedAt

Reglas:
- una plataforma pertenece al usuario o se maneja como catálogo global según la decisión de arquitectura
- inicialmente preferir una implementación simple y consistente con el resto del dominio

### Session
Representa una sesión de uso de un vehículo por parte de un conductor.

Posibles atributos:
- id
- userId
- driverId
- vehicleId
- platformId
- startTime
- endTime
- initialMileage
- finalMileage
- initialFuelLevel
- finalFuelLevel
- notes
- status
- createdAt
- updatedAt

Estados sugeridos:
- OPEN
- CLOSED
- CANCELLED

---

## Reglas de negocio actuales

### Ownership y seguridad
- un usuario solo puede operar sobre sus propios vehículos, conductores, plataformas y sesiones
- nunca devolver ni modificar recursos que pertenezcan a otro usuario

### Vehículos
- un vehículo no puede tener más de una sesión abierta al mismo tiempo

### Conductores
- un conductor no puede iniciar dos sesiones activas simultáneamente si la lógica del negocio lo prohíbe
- por defecto, asumir que un conductor no debe tener dos sesiones abiertas al mismo tiempo

### Sesiones
- una sesión debe tener kilometraje inicial al abrirse
- una sesión abierta no tiene kilometraje final ni combustible final
- al cerrar una sesión deben registrarse los datos finales
- el kilometraje final no puede ser menor al kilometraje inicial
- la fecha de fin no puede ser anterior a la fecha de inicio
- no se puede cerrar una sesión ya cerrada
- no se puede abrir una sesión para un vehículo que ya tiene una sesión abierta
- no se puede abrir una sesión para un conductor que ya tiene una sesión abierta, salvo que el negocio indique lo contrario

### Combustible
- el combustible debe manejarse con una convención clara desde el inicio
- elegir una sola representación y respetarla en toda la API:
    - porcentaje de 0 a 100
    - litros
    - fracción del tanque
- no mezclar formatos

---

## Métricas esperadas
A partir de las sesiones se podrán calcular, entre otras:

- kilómetros recorridos por sesión
- kilómetros recorridos por vehículo
- kilómetros recorridos por conductor
- cantidad de sesiones por período
- consumo estimado según variación de combustible
- uso por plataforma
- tiempo total de uso por conductor
- tiempo total de uso por vehículo

Claude debe priorizar guardar datos correctos y consistentes antes que sobre-optimizar cálculos.

---

## Arquitectura esperada

Usar arquitectura por capas:

- controller
- service
- repository
- entity
- dto
- mapper
- exception

### Reglas de arquitectura
- los controllers solo exponen endpoints y delegan al service
- la lógica de negocio va en services
- los repositories solo acceden a datos
- no exponer entidades JPA directamente
- usar DTOs para requests y responses
- usar mappers explícitos o MapStruct si el proyecto ya lo usa
- centralizar errores con `@ControllerAdvice`

---

## Convenciones de código
- usar nombres claros en inglés para clases, métodos y atributos
- clases en PascalCase
- métodos y variables en camelCase
- endpoints REST consistentes
- respuestas JSON predecibles
- evitar métodos demasiado largos
- evitar `any` equivalente o estructuras sin tipado claro
- preferir código simple, mantenible y testeable

---

## Convenciones para endpoints
Seguir REST de forma consistente.

Ejemplos esperados:
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/vehicles`
- `POST /api/vehicles`
- `GET /api/drivers`
- `POST /api/drivers`
- `GET /api/platforms`
- `POST /api/platforms`
- `POST /api/sessions`
- `PATCH /api/sessions/{id}/close`
- `GET /api/sessions`
- `GET /api/metrics/...`

No inventar endpoints innecesarios si ya existe un patrón en el proyecto.

---

## Validaciones obligatorias
- validar request bodies con Jakarta Validation
- validar existencia de entidades relacionadas antes de operar
- validar ownership del recurso
- validar que `finalMileage >= initialMileage`
- validar que no existan sesiones activas conflictivas
- validar que plataforma, conductor y vehículo pertenezcan al usuario autenticado
- validar formato y unicidad según reglas del negocio

---

## Persistencia y JPA
- usar relaciones JPA con cuidado
- evitar `EAGER` por defecto salvo necesidad real
- preferir `LAZY`
- evitar problemas de N+1 queries
- usar consultas explícitas cuando una validación lo requiera
- no diseñar entidades excesivamente acopladas

---

## Manejo de fechas
- usar tipos de fecha/hora adecuados (`LocalDateTime`, `Instant`, u otro estándar definido por el proyecto)
- ser consistente en toda la API
- documentar timezone si aplica
- no mezclar varios criterios temporales sin necesidad

---

## Antes de modificar código
Siempre:
1. analizar la estructura actual del proyecto
2. identificar controladores, servicios, entidades y repositorios relacionados
3. explicar brevemente el plan
4. proponer cambios mínimos y seguros
5. respetar el estilo ya existente

---

## Al implementar una nueva feature
Seguir este orden:

1. definir caso de uso
2. revisar entidades afectadas
3. crear o ajustar DTOs
4. implementar lógica de negocio en service
5. persistencia en repository
6. exponer endpoint en controller
7. agregar validaciones
8. agregar manejo de errores
9. agregar tests si el proyecto ya usa testing

---

## Testing
Priorizar tests para lógica de negocio.

Cubrir al menos:
- creación de vehículo
- creación de conductor
- creación de plataforma
- apertura de sesión
- cierre de sesión
- intento de abrir sesión duplicada para el mismo vehículo
- intento de cerrar sesión con kilometraje inválido
- acceso a recursos de otro usuario

Si hay tests de integración, priorizar endpoints críticos de sesiones.

---

## Seguridad
Si el proyecto ya tiene autenticación:
- usar siempre el usuario autenticado como fuente de ownership
- no confiar en `userId` enviado por el cliente cuando no sea necesario

Si el proyecto todavía no tiene seguridad implementada:
- diseñar el código de forma que luego sea fácil integrar Spring Security y JWT

---

## Qué debe hacer Claude en este repo
Claude debe actuar como un senior backend engineer enfocado en:
- diseño limpio
- consistencia del dominio
- validaciones correctas
- cambios pequeños
- claridad del código
- mantenimiento futuro

Siempre debe:
- respetar la arquitectura existente
- priorizar simplicidad
- evitar sobreingeniería
- no introducir pagos, cobros o alquileres si no fueron pedidos
- no crear abstractions innecesarias demasiado pronto

---

## Qué NO debe hacer Claude
- no agregar módulos de pagos o facturación
- no asumir lógica financiera inexistente
- no mezclar lógica de métricas con lógica de persistencia
- no saltarse la capa service
- no exponer entidades JPA directamente
- no modificar recursos de otros usuarios
- no introducir complejidad innecesaria
- no reestructurar todo el proyecto sin necesidad clara

---

## Formato de trabajo esperado
Cuando se pida implementar algo, Claude debe:
1. explicar el enfoque en pocas líneas
2. identificar archivos a tocar
3. hacer cambios consistentes con el código actual
4. indicar cómo probar
5. mencionar riesgos, validaciones faltantes o mejoras futuras

---

## Futuras extensiones posibles
Estas ideas pueden existir más adelante, pero no deben implementarse salvo pedido explícito:
- pagos
- liquidaciones
- mantenimiento de vehículos
- multas
- gastos operativos
- reportes avanzados
- dashboards
- roles y permisos
- integración con plataformas externas

---

## Comandos comunes
Ajustar según el proyecto use Maven o Gradle.

### Maven
- run: `./mvnw spring-boot:run`
- test: `./mvnw test`
- build: `./mvnw clean package`

### Gradle
- run: `./gradlew bootRun`
- test: `./gradlew test`
- build: `./gradlew build`

---

## Nota de diseño importante
Este proyecto está en una etapa inicial centrada en operación y trazabilidad.

La prioridad actual es:
1. registrar correctamente la información
2. asegurar consistencia de sesiones
3. preparar una buena base para métricas futuras

La prioridad actual NO es monetización ni facturación.