# Dockerfile para construir y ejecutar la aplicación Spring Boot

# ========== ETAPA 1: BUILD ==========
FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace

# Copiar todo el código fuente al contenedor
COPY . /workspace

# Dar permisos de ejecución a gradlew y ejecutar bootJar (skip tests para acelerar)
RUN chmod +x ./gradlew && \
    ./gradlew bootJar --no-daemon -x test

# ========== ETAPA 2: RUNTIME ==========
FROM eclipse-temurin:21-jre

WORKDIR /app

# Exponer el puerto 8080 (donde corre Spring Boot)
EXPOSE 8080

# Copiar SOLO el JAR generado desde la etapa de build
COPY --from=build /workspace/build/libs/global-0.0.1-SNAPSHOT.jar /app/app.jar

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
