#  CATALOG DCOKERFILE

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY catalog-service/pom.xml ./
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /build/target/catalog-service-*.jar ./app.jar

ARG APP_VERSION=1.0.0
ARG PROFILE=dev
EXPOSE 8080

# Fixed LegacyKeyValueFormat, spacing, and invalid hyphens in keys
ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${PROFILE}
ENV URL=jdbc:mysql://catalog-service:3306/catalog

# Fixed the entrypoint to run the exact copied filename and fixed typo in property name
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${URL} app.jar"]
