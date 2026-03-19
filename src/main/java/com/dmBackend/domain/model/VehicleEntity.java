package com.dmBackend.domain.model;

import com.dmBackend.domain.model.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 20)
    private String plate;

    @Column(name = "year_model")
    private Integer yearModel;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}