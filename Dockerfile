FROM maven:3.9.9-eclipse-temurin-22-jammy AS build
COPY . .
RUN mvn clean package -DskipTests
FROM eclipse-temurin:22-jammy
COPY --from=build /target/Employee-Management-Backend-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]