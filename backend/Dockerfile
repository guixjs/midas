FROM maven:3.9.6-eclipse-temurin-17 as builder

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install
