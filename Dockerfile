# Etapa 1: Compilar la aplicación usando Maven estándar
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación compilada
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY wallet /app/wallet
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
