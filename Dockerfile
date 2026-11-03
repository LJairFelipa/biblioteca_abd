# Etapa 1: Compilar la aplicación con Maven en la nube
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
# Ejecuta el empaquetado usando el Maven wrapper del proyecto
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecutar la aplicación compilada
FROM eclipse-temurin:17-jdk
WORKDIR /app
# Copia el JAR generado en la primera etapa
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
# Copia la carpeta del wallet de Oracle
COPY wallet /app/wallet
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
