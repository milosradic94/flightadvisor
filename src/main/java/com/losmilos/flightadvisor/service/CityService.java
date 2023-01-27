package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.model.domain.City;
import com.losmilos.flightadvisor.model.dto.response.CityResponseWithComments;
import com.losmilos.flightadvisor.model.mapper.CityMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.repository.CityRepository;
import com.losmilos.flightadvisor.repository.CommentRepository;
import com.losmilos.flightadvisor.utility.Constants.CacheNames;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CityService {

    private final CityRepository cityRepository;

    private final CommentRepository commentRepository;

    private final CityMapperImpl cityMapper;

    public City addCity(City city) {
        CityEntity cityEntity = cityRepository.save(cityMapper.domainToEntity(city));

        return cityMapper.entityToDomain(cityEntity);
    }

    @Cacheable(value = CacheNames.CITIES_WITH_COMMENTS)
    public List<CityResponseWithComments> getCities(String searchByName, Integer numberOfComments) {
        final var cities = searchByName != null ? cityRepository.findAllByNameIsContaining(searchByName) : cityRepository.findAll();

        for (var city:
             cities) {
            city.setComments(commentRepository.findAllByCityIdOrderByIdDesc(city.getId(), PageRequest.of(0, numberOfComments)).stream().collect(Collectors.toList()));
        }

        return cities.stream().map(cityMapper::entityToResponse).toList();
    }
}
