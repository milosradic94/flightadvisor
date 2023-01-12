package com.losmilos.flightadvisor.model.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class CheapestFlightResponse {

    private List<RouteResponse> routes;

    private Double total;

    private Integer steps;
}
