package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.AirportEntity;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, Long> {

    @Query("select a from AirportEntity a")
    @EntityGraph(attributePaths = {"city"})
    List<AirportEntity> findAllWithCity();

    Optional<AirportEntity> findById(Long id);
}
