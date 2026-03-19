# ===============================
# Etapa 1: Build de la aplicación
# ===============================
FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /app

# Pom y descargamos dependencia
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Código fuente y compilamos el jar
COPY src ./src

RUN mvn clean package -DskipTests

# ===============================
# Etapa 2: Imagen
# ===============================
FROM eclipse-temurin:23-jre-jammy

WORKDIR /app

# Artefacto final desde la etapa anterior
COPY --from=builder /app/target/dmBackend-0.0.1-SNAPSHOT.jar app.jar

# Puerto
EXPOSE 8088

# Perfil
ENV SPRING_PROFILES_ACTIVE=dev

# Entrypoint
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
