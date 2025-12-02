# Build stage
FROM gradle:7.4.1-jdk-alpine as BUILD
RUN gradle --version && java -version
WORKDIR /app
COPY build.gradle settings.gradle /app/
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY ./ /app/
USER root
RUN gradle clean build --no-daemon