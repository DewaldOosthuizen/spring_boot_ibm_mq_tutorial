## Building the actual application
FROM gradle:jdk11 as builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
ARG springprofileactive
ENV SPRING_PROFILES_ACTIVE=${springprofileactive}
RUN gradle build –stacktrace


## Running application
FROM openjdk:11-jre-slim
ARG springprofileactive
ENV SPRING_PROFILES_ACTIVE=${springprofileactive}
ENV JAVA_APP_JAR demo-*.jar
COPY --from=builder /home/gradle/src/build/libs/$JAVA_APP_JAR /app/demo.jar
EXPOSE 8080
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]