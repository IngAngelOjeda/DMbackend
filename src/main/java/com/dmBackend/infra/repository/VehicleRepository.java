package com.dmBackend.infra.repository;

import com.dmBackend.domain.model.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Page<VehicleEntity> findByUserId(String userId, Pageable pageable);

    Optional<VehicleEntity> findByIdAndUserId(Long id, String userId);

    boolean existsByUserIdAndPlate(String userId, String plate);

    boolean existsByUserIdAndPlateAndIdNot(String userId, String plate, Long id);

    long countByUserId(String userId);
}