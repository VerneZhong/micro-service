#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-course-dubbo-service
docker rm zxb-course-dubbo-service
docker image rm -f course-dubbo-service
docker build -t 192.168.1.139:8080/micro-service/course-dubbo-service:latest .
#docker run -d --name zxb-course-dubbo-service -p 8083:8083 course-dubbo-service:latest

docker push 192.168.1.139:8080/micro-service/course-dubbo-service:latest

