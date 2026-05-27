FROM ubuntu:latest as build
WORKDIR /build
COPY pom.xml .
CMD mvn offline-download

COPY src /app/src
CMD mvn clean compile


FROM ubuntu:latest
WORKDIR /app
COPY /User/target/User-*.jar /app


EXPOSE 8081
ARG APP_VERSION=1.0.0
ARG PROFILE=dev

ENV JAR_VERSION = ${APP_VERSION}
ENV ACTIVE-PROFILE =${PROFILE}

CMD java -jar -Dspring.profiles.active=${JAR_VERSION} -Dspring.profile.active=${ACTIVE-PROFILE} User-.jar