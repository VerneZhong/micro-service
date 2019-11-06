FROM 192.168.1.139:8080/micro-service/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/message-thrift-service-1.0-SNAPSHOT.jar /message-service.jar

ENTRYPOINT ["java", "-jar", "/message-service.jar"]