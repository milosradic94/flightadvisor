FROM maven:3.8.1-openjdk-17

WORKDIR /flightadvisor
COPY . .
RUN mvn clean install -Dmaven.test.skip=true

CMD mvn spring-boot:run