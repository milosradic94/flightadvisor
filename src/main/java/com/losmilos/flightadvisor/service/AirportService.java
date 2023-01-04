package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.model.domain.Airport;
import com.losmilos.flightadvisor.model.mapper.AirportMapperImpl;
import com.losmilos.flightadvisor.model.persistance.CityEntity;
import com.losmilos.flightadvisor.repository.AirportRepository;
import com.losmilos.flightadvisor.repository.CityRepository;
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
public class AirportService {

    private final CityRepository cityRepository;

    private final AirportRepository airportRepository;

    private final AirportMapperImpl airportMapper;

    @Async
    public void importCsv(MultipartFile file) {
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Airport> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Airport.class)
                    .build();

            List<Airport> airports = csvToBean.parse();

            airportRepository.saveAll(
                    airports.stream()
                            .map(this::mapCityId)
                            .filter(airport -> airport.getCityId() != null)
                            .map(airportMapper::domainToEntity)
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            // TODO: 04/01/2023 Send email about failure 
        }
    }

    private Airport mapCityId(Airport airport) {
        Optional<CityEntity> city = cityRepository.findByNameAndCountry(airport.getCity(), airport.getCountry());
        airport.setCityId(city.isPresent() ? city.get().getId() : null);
        return airport;
    }
}
