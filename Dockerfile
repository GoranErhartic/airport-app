FROM amazoncorretto:11-alpine-jdk
LABEL maintaner="goran.ns@gmail.com"
COPY app-1.0.jar app-1.0.jar
ENTRYPOINT ["java","-jar","/app-1.0.jar"]