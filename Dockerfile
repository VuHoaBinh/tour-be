FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/docker-spring-boot-postgres-1.0.0.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-spring-boot-postgres-1.0.0.jar"]