FROM maven:3.9.6-eclipse-temurin-17 as builder

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=builder /app/target/backend-1.jar /app/app.jar

WORKDIR /app
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

