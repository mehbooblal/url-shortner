FROM ubuntu:latest
LABEL authors="admin"

ENTRYPOINT ["top", "-b"]

# Using base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

COPY target/shorturl-0.0.1-SNAPSHOT.jar app.jar

# Expose a port (optional, if app runs on a specific port)
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
