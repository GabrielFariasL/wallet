FROM openjdk:21-jdk
WORKDIR /app
COPY target/wallet-api.jar wallet-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wallet-api.jar"]