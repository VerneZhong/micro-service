FROM hub.c.163.com/library/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]