FROM openjdk:17-jdk-alpine
RUN apk --no-cache add curl
ARG JAR_FILE=target/disgustingPocJava*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]