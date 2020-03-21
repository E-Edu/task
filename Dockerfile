# Please fill out with content

FROM mariadb:latest

ENV PACKAGES openssh-server openssh-client

RUN apt-get update && apt-get install -y $PACKAGES

