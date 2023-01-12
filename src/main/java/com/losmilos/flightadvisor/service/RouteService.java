package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.exception.NotFoundException;
import com.losmilos.flightadvisor.model.domain.Route;
import com.losmilos.flightadvisor.model.domain.dijkstra.Dijkstra;
import com.losmilos.flightadvisor.model.domain.dijkstra.Edge;
import com.losmilos.flightadvisor.model.domain.dijkstra.Graph;
import com.losmilos.flightadvisor.model.domain.dijkstra.Vertex;
import com.losmilos.flightadvisor.model.dto.response.CheapestFlightResponse;
import com.losmilos.flightadvisor.model.dto.response.MessageResponse;
import com.losmilos.flightadvisor.model.mapper.RouteMapperImpl;
import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import com.losmilos.flightadvisor.repository.AirportRepository;
import com.losmilos.flightadvisor.repository.CityRepository;
import com.losmilos.flightadvisor.repository.RouteRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RouteService {

    private final AirportRepository airportRepository;

    private final RouteRepository routeRepository;

    private final RouteMapperImpl routeMapper;

    private final CityRepository cityRepository;

    public ResponseEntity<?> findCheapestFlight(Long sourceCityId, Long destinationCityId)
    {
        final var sourceCity =
                cityRepository.findById(sourceCityId)
                        .orElseThrow(() -> new NotFoundException("Source City Not Found!"));

        final var destinationCity =
                cityRepository.findById(destinationCityId)
                        .orElseThrow(() -> new NotFoundException("Destination City Not Found!"));

        final var airports = airportRepository.findAll();

        final var routes = routeRepository.findAll();

        final var sourceAirports = airports.stream()
                .filter(airport -> airport.getCity().getId().equals(sourceCity.getId()))
                .collect(Collectors.toList());

        if(sourceAirports.isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Source City doesn't have airports!"));

        final var destinationAirports = airports.stream()
                .filter(airport -> airport.getCity().getId().equals(destinationCity.getId()))
                .collect(Collectors.toList());

        if(destinationAirports.isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Destination City doesn't have airports!"));

        final var nodes = airports.stream()
                .map(airport -> new Vertex(
                    airport.getId(),
                    airport.getCity().getName()))
                .collect(Collectors.toList());

        final var nodesMap = nodes.stream()
            .collect(Collectors.toMap(Vertex::getId, Function.identity()));

        final var edges = routes.stream()
                .map(route -> new Edge(
                    route.getId(),
                    nodesMap.get(route.getSourceAirport().getId()),
                    nodesMap.get(route.getDestinationAirport().getId()),
                    route.getPrice()))
                .collect(Collectors.toList());

        final var graph = new Graph(nodes, edges);
        final var dijkstra = new Dijkstra(graph);

        final var shortestRoute = sourceAirports.stream()
                .flatMap(
                        source -> destinationAirports.stream().map(destination ->
                                dijkstra.findCheapestPath(
                                        nodesMap.get(source.getId()),
                                        nodesMap.get(destination.getId())
                                )))
                .filter(route -> !route.isEmpty())
                .reduce(new ArrayList<>(), (a, b) ->
                        a.stream().map(Edge::getPrice).reduce(0d, (priceA, priceB) -> priceA + priceB) >
                                b.stream().map(Edge::getPrice).reduce(0d, (priceA, priceB) -> priceA + priceB) ? a : b);

        if(shortestRoute.isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Route can not be found!"));

        return ResponseEntity.ok(CheapestFlightResponse.builder()
            .routes(shortestRoute.stream().map(routeMapper::edgeToRoute).collect(Collectors.toList()))
            .total(shortestRoute.stream().map(Edge::getPrice).reduce(0d, (a, b) -> a + b))
            .steps(shortestRoute.size())
            .build());
    }

    @Async
    public void importCsv(MultipartFile file) {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Route> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Route.class)
                    .build();

            List<Route> routes = csvToBean.parse();

            routeRepository.saveAll(
                    routes.stream()
                            .map(this::mapSourceAndDestinationAirportId)
                            .filter(route -> !route.getSourceAirportId().equals(0l) && !route.getDestinationAirportId().equals(0L))
                            .map(routeMapper::domainToEntity)
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            // TODO: 04/01/2023 Send email about failure
        }
    }

    private Route mapSourceAndDestinationAirportId(Route route) {
        Optional<AirportEntity> sourceAirport = airportRepository.findById(route.getSourceAirportId());
        if(!sourceAirport.isPresent() || (!sourceAirport.get().getIata().equalsIgnoreCase(route.getSourceAirport()) && !sourceAirport.get().getIcao().equalsIgnoreCase(route.getSourceAirport()))) {
            route.setSourceAirportId(null);
            return route;
        }

        Optional<AirportEntity> destinationAirport = airportRepository.findById(route.getDestinationAirportId());
        if(!destinationAirport.isPresent() || (!destinationAirport.get().getIata().equalsIgnoreCase(route.getDestinationAirport()) && !destinationAirport.get().getIcao().equalsIgnoreCase(route.getDestinationAirport()))) {
            route.setDestinationAirportId(null);
            return route;
        }

        return route;
    }
}
