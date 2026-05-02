/FROM ubuntu:latest as build
WORKDIR /build
COPY pom.xml .
CMD mvn dependency:go-offline

COPY src ./src
CMD mvn clean package -DskipTests


FROM ubuntu:latest
WORKDIR /app
COPY --from=build /build/User/target/User-*.jar /app


EXPOSE 8081
ARG APP_VERSION=1.0.0
ARG PROFILE=dev

ENV JAR_VERSION = ${APP_VERSION}
ENV ACTIVE-PROFILE =${PROFILE}
ENV URl = jdbc:mysql://mysql:3306:db

CMD java -jar -Dspring.profiles.active=${JAR_VERSION} -Dspring.datasource.url= ${URL} -Dspring.profile.active=${ACTIVE-PROFILE} User-.jar