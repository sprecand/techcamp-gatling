FROM openjdk:21-jdk-slim
RUN apt-get update && apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
WORKDIR /gatling-backend
COPY . .
RUN cd gatling-backend && mvn clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gatling-backend/target/gatling-backend-0.0.1-SNAPSHOT.jar"]