FROM openjdk:8-jdk-alpine
COPY target/*.jar ems.jar
EXPOSE 10001
ENTRYPOINT ["java","-jar","/ems.jar"]