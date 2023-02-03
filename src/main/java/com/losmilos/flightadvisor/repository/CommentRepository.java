package com.losmilos.flightadvisor.repository;

import com.losmilos.flightadvisor.model.persistance.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    <T> Optional<T> findByIdAndUserId(Long id, Long userId, Class<T> type);

    Optional<CommentEntity> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long cityId);
}
