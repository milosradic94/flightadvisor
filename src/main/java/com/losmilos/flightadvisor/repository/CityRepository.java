package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.CityEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    @Query("select c from CityEntity c left outer join c.comments com on (com is null or com.inappropriate = false) order by c.id")
    @EntityGraph(attributePaths = {"comments"})
    List<CityEntity> findAllWithComments();

    @Query("select c from CityEntity c left outer join c.comments com on (com is null or com.inappropriate = false) where c.name like %:name% order by c.id")
    @EntityGraph(attributePaths = {"comments"})
    List<CityEntity> findAllByNameIsContainingWithComments(String name);

    Optional<CityEntity> findByNameAndCountry(String name, String country);
}
