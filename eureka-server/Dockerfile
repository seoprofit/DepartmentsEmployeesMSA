FROM bellsoft/liberica-openjdk-debian:17

RUN  apt-get update

RUN  apt-get install -y maven

WORKDIR /app

COPY src /app/src

COPY pom.xml /app/pom.xml

RUN mvn package -DskipTests

ENTRYPOINT ["java", "-jar", "/app/target/eureka-server-0.0.1-SNAPSHOT.jar"]