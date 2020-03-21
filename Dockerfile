FROM adoptopenjdk/openjdk11:alpine-jre
FROM mariadb:latest

ENV PACKAGES openssh-server openssh-client

WORKDIR /eedu

COPY /target/task-service-0.1-SNAPSHOT.jar taskms.jar

RUN apk --update --no-cache add tzdata \
  && cp /usr/share/zoneinfo/UTC /etc/localtime \
  && echo "UTC" >/etc/timezone \
  && apk --no-cache del tzdata \
  && apt-get update \
  && apt-get install -y $PACKAGES

ENTRYPOINT ["java", "-jar", "taskms.jar"]
