package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    List<CityEntity> findAllByNameIsContaining(String name);

    Optional<CityEntity> findByNameAndCountry(String name, String country);
}
