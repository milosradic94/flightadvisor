package com.losmilos.flightadvisor.model.domain.dijkstra;

import java.util.*;

public class Dijkstra {

    private final List<Vertex> nodes;

    private final List<Edge> edges;

    private Set<Vertex> settledNodes;

    private Set<Vertex> unsettledNodes;

    private Map<Vertex, Vertex> predecessors;

    private Map<Vertex, Double> price;

    public Dijkstra(Graph graph) {
        this.nodes = new ArrayList<>(graph.getVertexes());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public List<Edge> findCheapestPath(Vertex source, Vertex destination) {
        settledNodes = new HashSet<>();
        unsettledNodes = new HashSet<>();
        price = new HashMap<>();
        predecessors = new HashMap<>();

        price.put(source, 0d);
        unsettledNodes.add(source);

        while (unsettledNodes.size() > 0) {
            Vertex node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalPrices(node);
        }

        final var path = getPath(destination);

        List<Edge> shortestPath = new LinkedList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            final var index = i;
            shortestPath.add(
                    edges.stream().filter(edge ->
                                    edge.getSource().equals(path.get(index)) &&
                                            edge.getDestination().equals(path.get(index + 1)))
                    .findFirst()
                    .get());
        }

        return shortestPath;
    }

    private void findMinimalPrices(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);

        for (Vertex target : adjacentNodes) {
            if (getCheapestPrice(target) > getCheapestPrice(node)
                    + getPrice(node, target)) {
                price.put(target, getCheapestPrice(node)
                        + getPrice(node, target));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }

    private Double getPrice(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getPrice();
            }
        }

        throw new RuntimeException("Error finding cheapest flight!");
    }

    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }

        return neighbors;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;

        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getCheapestPrice(vertex) < getCheapestPrice(minimum)) {
                    minimum = vertex;
                }
            }
        }

        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private Double getCheapestPrice(Vertex destination) {
        Double d = price.get(destination);

        if (d == null) {
            return Double.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;

        if (predecessors.get(step) == null) {
            return path;
        }

        path.add(step);

        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }
}
