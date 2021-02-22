FROM openjdk:11
ARG JAR_FILE=target/*/*.jar
COPY ${JAR_FIlE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


