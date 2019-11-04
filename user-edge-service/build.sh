#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-user-edge-service
docker rm zxb-user-edge-service
docker image rm -f user-edge-service
docker build -t 192.168.1.137:8080/micro-service/user-edge-service:latest .
#docker run -d --name zxb-user-edge-service -p 8082:8082 user-edge-service:latest

docker push 192.168.1.137:8080/micro-service/user-edge-service:latest