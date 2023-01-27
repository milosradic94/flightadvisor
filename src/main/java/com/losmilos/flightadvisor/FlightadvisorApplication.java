package com.losmilos.flightadvisor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Flight Advisor API"))
public class FlightadvisorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightadvisorApplication.class, args);
    }

}
