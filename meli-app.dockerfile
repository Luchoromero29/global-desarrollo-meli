# ========== ETAPA 1: BUILD ==========
FROM gradle:8.10-jdk17-alpine AS build

# Copiar el código fuente al contenedor
COPY . /home/app

WORKDIR /home/app

# Construir JAR con Gradle
RUN gradle bootJar --no-daemon

# ========== ETAPA 2: RUNTIME ==========
FROM openjdk:17-alpine

# Exponer puerto 8080
EXPOSE 8080

# Copiar el JAR generado desde la etapa de build
COPY --from=build /home/app/build/libs/*.jar /app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
