#!/usr/bin/env bash

mvn -Dmaven.test.skip -U clean package

docker stop zxb-user-thrift-service
docker rm zxb-user-thrift-service
docker image rm -f user-service
docker build -t 192.168.1.139:8080/micro-service/user-service:latest .
# docker run -d -it user-service:latest -p 8080:8080 --mysql.address=192.168.0.101
#docker run -d -p 8080:8080 --name zxb-user-thrift-service user-service:latest


# MySQL数据库可以用任意ip连接访问的方法
# update user set host='%' where host='localhost';
# flush privileges;

docker push 192.168.1.139:8080/micro-service/user-service:latest