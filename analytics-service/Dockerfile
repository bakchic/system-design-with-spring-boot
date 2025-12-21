FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY target/analytics-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
