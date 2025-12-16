FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/catalog-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
