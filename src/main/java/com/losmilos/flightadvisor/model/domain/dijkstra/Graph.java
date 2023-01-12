package com.losmilos.flightadvisor.model.domain.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Graph {

    private final List<Vertex> vertexes;

    private final List<Edge> edges;
}
