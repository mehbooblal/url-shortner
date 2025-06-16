FROM ubuntu:latest
LABEL authors="admin"

ENTRYPOINT ["top", "-b"]

FROM openjdk:17-jdk-slim
COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
