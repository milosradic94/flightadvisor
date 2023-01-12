package com.losmilos.flightadvisor.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RouteResponse {

    private String source;

    private String destination;

    private Double price;

}
