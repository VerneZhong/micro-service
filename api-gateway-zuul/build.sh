#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-api-gateway-zuul
docker rm zxb-api-gateway-zuul
docker image rm -f api-gateway-zuul
#docker build -t api-gateway-zuul:latest .
docker build -t api-gateway-zuul:latest .
#docker run -d --name zxb-api-gateway-zuul -p 8085:8085 api-gateway-zuul:latest

# push 到 harbor 镜像仓库
docker tag api-gateway-zuul:latest 192.168.1.137:8080/micro-service/api-gateway-zuul:latest
docker push 192.168.1.137:8080/micro-service/api-gateway-zuul:latest