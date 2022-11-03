FROM openjdk:8-jdk-alpine

ARG JAR_FILE=build/libs/gift-certificate-1.0-SNAPSHOT.jar

COPY ${JAR_FILE} certificate-service.jar