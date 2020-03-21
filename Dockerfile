FROM adoptopenjdk/openjdk11:alpine-jre
FROM mariadb:latest

ENV PACKAGES openssh-server openssh-client

WORKDIR /eedu

COPY /target/task-service-0.1-SNAPSHOT.jar taskms.jar

ENTRYPOINT ["java", "-jar", "taskms.jar"]
