FROM eclipse-temurin:21-jdk
VOLUME /tmp
COPY target/payment-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]