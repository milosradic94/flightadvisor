package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
}
