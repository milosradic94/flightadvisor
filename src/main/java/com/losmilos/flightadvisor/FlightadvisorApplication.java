package com.losmilos.flightadvisor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FlightadvisorApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightadvisorApplication.class, args);
    }

}
