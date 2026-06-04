# USER DOCKERFILE
FROM maven:3.9-eclipse-temurin-17  as build
WORKDIR /build
COPY pom.xml .
COPY user-service/pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /build/target/user-service-*.jar ./app.jar


EXPOSE 8081
ARG APP_VERSION=1.0.0
ARG PROFILE=dev

ENV JAR_VERSION = ${APP_VERSION}
ENV ACTIVE-PROFILE =${PROFILE}
ENV URL=jdbc:mysql://user-db:3306/user

RUN java -jar -Dspring.profiles.active=${JAR_VERSION} -Dspring.profile.active=${ACTIVE-PROFILE} -Dspring.datasource.url=${URL} User-.jar