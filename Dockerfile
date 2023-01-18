FROM maven:3.8.1-openjdk-17

WORKDIR /flightadvisor
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

CMD mvn spring-boot:run

#FROM openjdk:17-alpine
#COPY target/flightadvisor-0.0.1-SNAPSHOT.jar /flightadvisor-0.0.1-SNAPSHOT.jar
#CMD ["java", "-jar", "/flightadvisor-0.0.1-SNAPSHOT.jar"]