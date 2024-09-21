FROM eclipse-temurin:17
WORKDIR /app
COPY target/workshop-*.jar workshop.jar
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "workshop.jar"]
