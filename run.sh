#!/usr/bin/env sh

sh build.sh
sudo sh buildDocker.sh

sudo docker-compose up
sudo docker-compose down
