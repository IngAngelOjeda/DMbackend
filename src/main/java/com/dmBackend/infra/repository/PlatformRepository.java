package com.dmBackend.infra.repository;

import com.dmBackend.domain.model.PlatformEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<PlatformEntity, Long> {

    Page<PlatformEntity> findByUserId(String userId, Pageable pageable);

    Optional<PlatformEntity> findByIdAndUserId(Long id, String userId);

    boolean existsByUserIdAndName(String userId, String name);
}