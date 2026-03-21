package com.dmBackend.infra.repository;

import com.dmBackend.domain.model.SessionEntity;
import com.dmBackend.domain.model.enums.SessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    /**
     * Fetches sessions with all related entities in a single query to avoid N+1.
     * COUNT query is separate to support pagination correctly with JOIN FETCH.
     */
    @Query("""
            SELECT s FROM SessionEntity s
            JOIN FETCH s.driver
            JOIN FETCH s.vehicle
            JOIN FETCH s.platform
            WHERE s.userId = :userId
            """)
    List<SessionEntity> findByUserIdWithRelations(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT COUNT(s) FROM SessionEntity s WHERE s.userId = :userId")
    long countByUserId(@Param("userId") String userId);

    @Query("""
            SELECT s FROM SessionEntity s
            JOIN FETCH s.driver
            JOIN FETCH s.vehicle
            JOIN FETCH s.platform
            WHERE s.id = :id AND s.userId = :userId
            """)
    Optional<SessionEntity> findByIdAndUserIdWithRelations(@Param("id") Long id, @Param("userId") String userId);

    boolean existsByVehicleIdAndStatus(Long vehicleId, SessionStatus status);

    boolean existsByDriverIdAndStatus(Long driverId, SessionStatus status);

    @Query("SELECT s.status, COUNT(s) FROM SessionEntity s WHERE s.userId = :userId GROUP BY s.status")
    List<Object[]> countByUserIdGroupByStatus(@Param("userId") String userId);

    @Query("""
            SELECT COALESCE(SUM(s.finalMileage - s.initialMileage), 0)
            FROM SessionEntity s
            WHERE s.userId = :userId AND s.status = com.dmBackend.domain.model.enums.SessionStatus.CLOSED
            """)
    Long sumKilometersByUserId(@Param("userId") String userId);

    @Query("""
            SELECT s.vehicle.id, s.vehicle.plate, s.vehicle.brand, s.vehicle.model,
                   SUM(s.finalMileage - s.initialMileage)
            FROM SessionEntity s
            WHERE s.userId = :userId AND s.status = com.dmBackend.domain.model.enums.SessionStatus.CLOSED
            GROUP BY s.vehicle.id, s.vehicle.plate, s.vehicle.brand, s.vehicle.model
            ORDER BY SUM(s.finalMileage - s.initialMileage) DESC
            """)
    List<Object[]> findTopVehiclesByKilometers(@Param("userId") String userId, Pageable pageable);

    @Query("""
            SELECT s.driver.id, s.driver.firstName, s.driver.lastName,
                   SUM(s.finalMileage - s.initialMileage)
            FROM SessionEntity s
            WHERE s.userId = :userId AND s.status = com.dmBackend.domain.model.enums.SessionStatus.CLOSED
            GROUP BY s.driver.id, s.driver.firstName, s.driver.lastName
            ORDER BY SUM(s.finalMileage - s.initialMileage) DESC
            """)
    List<Object[]> findTopDriversByKilometers(@Param("userId") String userId, Pageable pageable);

    @Query("""
            SELECT s.platform.id, s.platform.name, COUNT(s),
                   COALESCE(SUM(CASE WHEN s.status = com.dmBackend.domain.model.enums.SessionStatus.CLOSED
                                     THEN s.finalMileage - s.initialMileage ELSE 0 END), 0)
            FROM SessionEntity s
            WHERE s.userId = :userId
            GROUP BY s.platform.id, s.platform.name
            ORDER BY COUNT(s) DESC
            """)
    List<Object[]> findSessionsByPlatform(@Param("userId") String userId);
}