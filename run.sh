#!/usr/bin/env sh

sh build.sh
sudo sh buildDocker.sh

echo "Search for sentry configuration"
# Checks whether the file "_.sentryEnabled" exists
sentryEnabled=false
if [ -e .sentryEnabled ]; then
	sentryEnabled=true
fi
echo "Sentry reporting enabled: ${sentryEnabled}"

sudo SENTRY_ENABLED="$sentryEnabled" docker-compose up
sudo docker-compose down
