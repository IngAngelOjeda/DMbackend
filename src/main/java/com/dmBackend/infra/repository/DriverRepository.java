package com.dmBackend.infra.repository;

import com.dmBackend.domain.model.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    Page<DriverEntity> findByUserId(String userId, Pageable pageable);

    Optional<DriverEntity> findByIdAndUserId(Long id, String userId);

    boolean existsByUserIdAndDocumentNumber(String userId, String documentNumber);

    long countByUserId(String userId);
}