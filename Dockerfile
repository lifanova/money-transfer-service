FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 8080

ADD build/libs/money-transfer-service-0.0.1-SNAPSHOT.jar money-transfer-service.jar

ENTRYPOINT ["java", "-jar", "/money-transfer-service.jar"]