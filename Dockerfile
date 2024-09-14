FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/PayCraft-Backend-0.0.1-SNAPSHOT.jar /app/paycraft.jar

EXPOSE 6020

CMD ["java", "-jar", "/app/paycraft.jar"]