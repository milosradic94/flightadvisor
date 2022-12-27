package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    Optional<AirportEntity> findById(Long id);
}
