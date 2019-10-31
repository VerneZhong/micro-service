#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-user-edge-service
docker rm zxb-user-edge-service
docker image rm -f user-edge-service
docker build -t user-edge-service:latest .
#docker run -d --name zxb-user-edge-service -p 8082:8082 user-edge-service:latest