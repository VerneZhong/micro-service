FROM 192.168.1.139:8080/micro-service/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/course-dubbo-service-1.0-SNAPSHOT.jar /course-dubbo-service.jar

ENTRYPOINT ["java", "-jar", "/course-dubbo-service.jar"]