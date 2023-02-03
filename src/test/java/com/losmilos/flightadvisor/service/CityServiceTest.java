package com.losmilos.flightadvisor.service;

import static org.mockito.Mockito.when;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.dto.response.CityResponseWithComments;
import com.losmilos.flightadvisor.model.dto.response.CommentResponse;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import com.losmilos.flightadvisor.model.persistance.RoleEntity;
import com.losmilos.flightadvisor.model.persistance.UserEntity;
import com.losmilos.flightadvisor.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    private static final RoleEntity ROLE_ENTITY = RoleEntity.builder()
            .id(1l)
            .role(com.losmilos.flightadvisor.enumeration.Role.ROLE_USER)
            .build();

    private static final UserEntity USER_ENTITY = UserEntity.builder()
            .id(1l)
            .firstName("DummyFirstName")
            .lastName("DummyLastName")
            .username("DummyUsername")
            .password("DummyPassword")
            .role(ROLE_ENTITY)
            .build();

    private static final CommentResponse COMMENT_RESPONSE = CommentResponse.builder()
            .id(1l)
            .description("DummyDescription")
            .userId(1l)
            .cityId(1l)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    private static final CityResponseWithComments CITY_RESPONSE_WITH_COMMENTS = CityResponseWithComments.builder()
            .id(1l)
            .name("DummyName")
            .country("DummyCountry")
            .description("DummyDescription")
            .comments(Collections.singletonList(COMMENT_RESPONSE))
            .build();

    private static final List<CommentEntity> COMMENT_ENTITY_LIST = Collections.singletonList(CommentEntity.builder()
        .id(1l)
        .description("DummyDescription")
        .user(USER_ENTITY)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build());

    private static final CityEntity CITY_ENTITY = CityEntity.builder()
        .id(1l)
        .name("DummyName")
        .country("DummyCountry")
        .description("DummyDescription")
        .comments(COMMENT_ENTITY_LIST)
        .build();

    private static final List<CityEntity> CITY_ENTITY_LIST = Collections.singletonList(CITY_ENTITY);

    private static final List<CityResponseWithComments> CITY_RESPONSE_WITH_COMMENTS_LIST = Collections.singletonList(CITY_RESPONSE_WITH_COMMENTS);

    @Test
    void addCity_ShouldReturnCityDomain_WhenCityIsSuccessfullyCreated() {
        when(cityMapper.domainToEntity(CITY)).thenReturn(CITY_ENTITY);
        when(cityRepository.save(CITY_ENTITY)).thenReturn(CITY_ENTITY);
        when(cityMapper.entityToDomain(CITY_ENTITY)).thenReturn(CITY);

        final var city = cityService.addCity(CITY);

        Assertions.assertEquals(city, CITY);
    }

    @Test
    void getCities_ShouldReturnCities_WhenAllParametersAreNull() {
        when(cityRepository.findAllWithComments()).thenReturn(CITY_ENTITY_LIST);
        when(cityMapper.entityToResponse(CITY_ENTITY)).thenReturn(CITY_RESPONSE_WITH_COMMENTS);

        final var cities = cityService.getCities(null, Integer.MAX_VALUE);

        Assertions.assertEquals(cities, CITY_RESPONSE_WITH_COMMENTS_LIST);
    }

    @Test
    void getCities_ShouldReturnCities_WhenAllParametersAreNotNull() {
        when(cityRepository.findAllByNameIsContainingWithComments("a")).thenReturn(CITY_ENTITY_LIST);
        when(cityMapper.entityToResponse(CITY_ENTITY)).thenReturn(CITY_RESPONSE_WITH_COMMENTS);

        final var cities = cityService.getCities("a", 1);

        Assertions.assertEquals(cities, CITY_RESPONSE_WITH_COMMENTS_LIST);
    }

    @Test
    void getCities_ShouldReturnCities_WhenSearchByNameIsNotNull() {
        when(cityRepository.findAllByNameIsContainingWithComments("a")).thenReturn(CITY_ENTITY_LIST);
        when(cityMapper.entityToResponse(CITY_ENTITY)).thenReturn(CITY_RESPONSE_WITH_COMMENTS);

        final var cities = cityService.getCities("a", Integer.MAX_VALUE);

        Assertions.assertEquals(cities, CITY_RESPONSE_WITH_COMMENTS_LIST);
    }

    @Test
    void getCities_ShouldReturnCities_WhenNumberOfCommentsIsNotNull() {
        when(cityRepository.findAllWithComments()).thenReturn(CITY_ENTITY_LIST);
        when(cityMapper.entityToResponse(CITY_ENTITY)).thenReturn(CITY_RESPONSE_WITH_COMMENTS);

        final var cities = cityService.getCities(null, 1);

        Assertions.assertEquals(cities, CITY_RESPONSE_WITH_COMMENTS_LIST);
    }
}
