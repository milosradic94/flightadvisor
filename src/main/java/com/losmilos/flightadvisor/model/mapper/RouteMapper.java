package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.Route;
import com.losmilos.flightadvisor.model.domain.dijkstra.Edge;
import com.losmilos.flightadvisor.model.dto.response.RouteResponse;
import com.losmilos.flightadvisor.model.persistance.RouteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RouteMapper {

    @Mapping(target = "sourceAirport.id", source = "sourceAirportId")
    @Mapping(target = "destinationAirport.id", source = "destinationAirportId")
    RouteEntity domainToEntity(Route route);

    @Mapping(target = "source", source = "source.cityName")
    @Mapping(target = "destination", source = "destination.cityName")
    RouteResponse edgeToRoute(Edge edge);
}
