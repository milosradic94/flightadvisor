package com.losmilos.flightadvisor.service;

import com.losmilos.flightadvisor.model.domain.Airport;
import com.losmilos.flightadvisor.model.mapper.AirportMapperImpl;
import com.losmilos.flightadvisor.model.persistance.AirportEntity;
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
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class AirportService {

    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;

    private final AirportMapperImpl airportMapper;

    @Async
    public void importCsv(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Airport> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Airport.class)
                    .build();

            List<Airport> airports = csvToBean.parse();
            List<AirportEntity> airportEntities = new ArrayList<AirportEntity>();

            final var cities = cityRepository.findAll();

            for (Airport airport:
                 airports) {
                Optional<CityEntity> city = cities.stream()
                        .filter(
                                cityEntity -> cityEntity.getName().equalsIgnoreCase(airport.getCity()) &&
                                cityEntity.getCountry().equalsIgnoreCase(airport.getCountry()))
                        .findFirst();

                if(city.isPresent()) {
                    airport.setCityId(city.get().getId());
                    airportEntities.add(airportMapper.domainToEntity(airport));
                }
            }

            airportRepository.saveAll(airportEntities);
        } catch (Exception e) {}
    }
}
