package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.enumeration.Role;
import com.losmilos.flightadvisor.model.persistance.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(Role role);
}
