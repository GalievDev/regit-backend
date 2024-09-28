FROM ubuntu:latest
LABEL authors="galiev"

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/regit-backend.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080
ENTRYPOINT ["top", "-b"]