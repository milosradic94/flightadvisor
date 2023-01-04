package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.model.domain.Route;
import com.losmilos.flightadvisor.model.mapper.RouteMapperImpl;
import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import com.losmilos.flightadvisor.repository.AirportRepository;
import com.losmilos.flightadvisor.repository.RouteRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RouteService {

    private final AirportRepository airportRepository;

    private final RouteRepository routeRepository;

    private final RouteMapperImpl routeMapper;

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
