package com.losmilos.flightadvisor.model.domain.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class Edge  {

    private final Long id;

    private final Vertex source;

    private final Vertex destination;

    private final Double price;
}
