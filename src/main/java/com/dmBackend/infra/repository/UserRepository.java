package com.dmBackend.infra.repository;

import com.dmBackend.domain.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByKeycloakId(String keycloakId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByDocumentNumber(String documentNumber);

    @Query("""
            SELECT u FROM UserEntity u
            WHERE (:search = '' OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                                OR LOWER(u.lastName)  LIKE LOWER(CONCAT('%', :search, '%'))
                                OR LOWER(u.email)     LIKE LOWER(CONCAT('%', :search, '%'))
                                OR LOWER(u.username)  LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<UserEntity> findBySearch(@Param("search") String search, Pageable pageable);
}