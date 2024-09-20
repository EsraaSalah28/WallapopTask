FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy your JAR file after other commands that don't change frequently
COPY WallapopTask.jar /app/WallapopTask.jar

ENTRYPOINT ["java", "-jar", "/app/WallapopTask.jar"]
