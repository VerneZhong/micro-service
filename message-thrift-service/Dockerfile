FROM hub.c.163.com/library/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/message-thrift-service-1.0-SNAPSHOT.jar /message-service.jar

ENTRYPOINT ["java", "-jar", "/message-service.jar"]