FROM hub.c.163.com/library/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/user-edge-service-1.0-SNAPSHOT.jar /user-edge-service.jar

ENTRYPOINT ["java", "-jar", "/user-edge-service.jar"]