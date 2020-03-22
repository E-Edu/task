FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /eedu

COPY /target/task-service-0.1-SNAPSHOT.jar taskms.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "taskms.jar"]
