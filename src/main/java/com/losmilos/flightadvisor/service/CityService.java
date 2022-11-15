package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    private final CityMapperImpl cityMapper;

    public City addCity(City city) {
        CityEntity cityEntity = cityRepository.save(cityMapper.domainToEntity(city));

        return cityMapper.entityToDomain(cityEntity);
    }
}
