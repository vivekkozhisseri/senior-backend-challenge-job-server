FROM gradle:7.4.1-jdk-alpine as BUILD
RUN gradle --version && java -version
WORKDIR /app
COPY build.gradle settings.gradle /app/
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY ./ /app/
USER root
RUN gradle clean build --no-daemon


FROM 218894879100.dkr.ecr.us-east-1.amazonaws.com/corretto-11-jdk-alpine:latest

ARG VERSION
ARG SPRING_ACTIVE_PROFILES
ARG JAR_FILE=/app/application/build/libs/application-0.0.1-SNAPSHOT.jar

ENV JAVA_OPTS="-Xmx2048m -Xms1024m"
ENV JMX_OPTS="\
    -Djava.security.egd=file:/dev/./urandom \
    -Dspring.profiles.active=${SPRING_ACTIVE_PROFILES} \
    -Dcom.sun.management.jmxremote.port=9016 \
    -Dcom.sun.management.jmxremote.rmi.port=9016 \
    -Dcom.sun.management.jmxremote.local.only=false \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.authenticate=false"

ENV DEFAULT_OPTS "-Dconfig.override_with_env_vars=true \
                  -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

EXPOSE 8080
EXPOSE 5005
EXPOSE 9016
COPY --from=0 ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c"]
CMD ["exec java ${JAVA_OPTS} ${JMX_OPTS} ${DEFAULT_OPTS} -jar app.jar"]