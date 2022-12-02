package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByIdAndUserId(Long id, Long userId);

    Page<CommentEntity> findAllByCityIdOrderByIdDesc(Long cityId, Pageable pageable);

    void deleteByIdAndUserId(Long id, Long cityId);
}
