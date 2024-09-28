FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build.gradle settings.gradle gradle.properties /app/

COPY src /app/src

RUN ./gradlew build --no-daemon

EXPOSE 8080

CMD ["./gradlew", "bootRun"]
