package com.losmilos.flightadvisor.model.mapper;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.dto.request.CityRequest;
import com.losmilos.flightadvisor.model.dto.response.CityResponse;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CityMapper {

    City dtoToDomain(CityRequest cityRequest);

    CityEntity domainToEntity(City city);

    City entityToDomain(CityEntity city);

    CityResponse domainToDto(City city);
}
