package com.losmilos.flightadvisor.model.domain.dijkstra;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Vertex {

    final private Long id;

    final private String cityName;
}
