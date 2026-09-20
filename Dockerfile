# Stage 1: Build con Gradle
FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Ejecución con OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
