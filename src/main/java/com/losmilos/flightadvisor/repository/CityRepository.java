package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    List<CityEntity> findAllByNameIsContaining(String name);
}
