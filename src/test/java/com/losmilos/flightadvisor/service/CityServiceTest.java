package com.losmilos.flightadvisor.service;

import static org.mockito.Mockito.when;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapperImpl cityMapper;

    @InjectMocks
    private CityService cityService;

    private static final City CITY = City.builder()
            .id(1l)
            .name("DummyName")
            .country("DummyCountry")
            .description("DummyDescription")
            .build();

    private static final CityEntity CITY_ENTITY = CityEntity.builder()
            .id(1l)
            .name("DummyName")
            .country("DummyCountry")
            .description("DummyDescription")
            .build();

    @Test
    void addCity_ShouldReturnCityDomain_WhenCityIsSuccessfullyCreated() {
        when(cityMapper.domainToEntity(CITY)).thenReturn(CITY_ENTITY);
        when(cityRepository.save(CITY_ENTITY)).thenReturn(CITY_ENTITY);
        when(cityMapper.entityToDomain(CITY_ENTITY)).thenReturn(CITY);

        final var city = cityService.addCity(CITY);

        Assertions.assertEquals(city, CITY);
    }
}
