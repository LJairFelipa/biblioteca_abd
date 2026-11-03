FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY demo-0.0.1-SNAPSHOT.jar.original app.jar
COPY wallet /app/wallet

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
