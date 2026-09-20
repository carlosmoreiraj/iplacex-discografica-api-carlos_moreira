# Stage 1: Build con Gradle
FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Ejecución con Java 17 (Eclipse Temurin)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
