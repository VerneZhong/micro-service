#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-message-thrift-service
docker rm zxb-message-thrift-service
docker image rm -f message-service
docker build -t 192.168.1.139:8080/micro-service/message-service:latest .
#docker run -d --name zxb-message-thrift-service -p 8081:8081 message-service:latest

docker push 192.168.1.139:8080/micro-service/message-service:latest