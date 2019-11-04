#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-course-edge-service
docker rm zxb-course-edge-service
docker image rm -f course-edge-service
docker build -t 192.168.1.137:8080/micro-service/course-edge-service:latest .
#docker run -d --name zxb-course-edge-service -p 8084:8084 course-edge-service:latest

docker push 192.168.1.137:8080/micro-service/course-edge-service:latest