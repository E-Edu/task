FROM adoptopenjdk/openjdk11:alpine-jre
FROM mariadb:latest

WORKDIR /eedu

COPY /target/task-service-0.1-SNAPSHOT.jar taskms.jar

ENTRYPOINT ["java", "-jar", "taskms.jar"]
