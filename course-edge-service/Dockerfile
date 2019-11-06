FROM 192.168.1.139:8080/micro-service/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/course-edge-service-1.0-SNAPSHOT.jar /course-edge-service.jar

ENTRYPOINT ["java", "-jar", "/course-edge-service.jar"]