FROM 192.168.1.137:8080/micro-service/java:latest
MAINTAINER zhongxb zhongxuebin17180062@gmail.com

COPY target/api-gateway-zuul-1.0-SNAPSHOT.jar /api-gateway-zuul.jar

ENTRYPOINT ["java", "-jar", "/api-gateway-zuul.jar"]