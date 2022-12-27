package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.Airport;
import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AirportMapper {

    @Mapping(target = "city.id", source = "cityId")
    AirportEntity domainToEntity(Airport airport);
}
