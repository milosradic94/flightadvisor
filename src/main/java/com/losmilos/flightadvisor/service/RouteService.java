package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.model.domain.Route;
import com.losmilos.flightadvisor.model.mapper.RouteMapperImpl;
import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import com.losmilos.flightadvisor.model.persistance.RouteEntity;
import com.losmilos.flightadvisor.repository.AirportRepository;
import com.losmilos.flightadvisor.repository.RouteRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class RouteService {

    private final AirportRepository airportRepository;

    private final RouteRepository routeRepository;

    private final RouteMapperImpl routeMapper;

    @Async
    public void importCsv(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Route> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Route.class)
                    .build();

            List<Route> routes = csvToBean.parse();
            List<RouteEntity> routeEntities = new ArrayList<RouteEntity>();

            final var airports = airportRepository.findAll();

            for (Route route:
                    routes) {
                Optional<AirportEntity> sourceAirport = airports.stream()
                        .filter(
                                airport ->
                                        airport.getId().equals(route.getSourceAirportId()) &&
                                                (airport.getIata().equalsIgnoreCase(route.getSourceAirport()) ||
                                                        airport.getIcao().equalsIgnoreCase(route.getSourceAirport())))
                        .findFirst();

                Optional<AirportEntity> destinationAirport = airports.stream()
                        .filter(
                                airport ->
                                        airport.getId().equals(route.getDestinationAirportId()) &&
                                                (airport.getIata().equalsIgnoreCase(route.getDestinationAirport()) ||
                                                        airport.getIcao().equalsIgnoreCase(route.getDestinationAirport())))
                        .findFirst();

                if(sourceAirport.isPresent() && destinationAirport.isPresent()) {
                    routeEntities.add(routeMapper.domainToEntity(route));
                }
            }

            routeRepository.saveAll(routeEntities);
        } catch (Exception e) {}
    }
}
