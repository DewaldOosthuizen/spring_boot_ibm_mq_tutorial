# ## Building the actual application
# FROM gradle:jdk11 as builder
# COPY --chown=gradle:gradle . /home/gradle/src
# WORKDIR /home/gradle/src
# ARG springprofileactive
# ENV SPRING_PROFILES_ACTIVE=${springprofileactive}
# RUN gradle build --stacktrace


# ## Running application
# FROM openjdk:11-jre-slim
# ARG springprofileactive
# ENV SPRING_PROFILES_ACTIVE=${springprofileactive}
# ENV JAVA_APP_JAR demo-*.jar
# COPY --from=builder /home/gradle/src/build/libs/$JAVA_APP_JAR /app/demo.jar
# EXPOSE 8080
# WORKDIR /app
# ENTRYPOINT ["java", "-jar", "/app/demo.jar"]


######### Single layer build #########
# Pull image to build
FROM openjdk:11.0-jre-stretch

# create args
ARG springprofileactive

# set environmental variables
ENV JAVA_APP_JAR demo-*.jar
ENV SPRING_PROFILES_ACTIVE=${springprofileactive}

# copy the jar file from local to work dir
# NOTE: requires you to run './gradlew clean build'
COPY build/libs/$JAVA_APP_JAR /app/demo.jar

# expose ports
EXPOSE 8080

# set work dir
WORKDIR /app

# start the app
ENTRYPOINT ["java", "-jar", "/app/demo.jar"]
####################################

