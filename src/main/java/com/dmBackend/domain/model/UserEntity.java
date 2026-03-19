package com.dmBackend.domain.model;

import com.dmBackend.domain.model.generic.RoleAPI;
import com.dmBackend.domain.model.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(name = "keycloak_id", unique = true)
    private String keycloakId;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "document_number", unique = true, length = 50)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RoleAPI role;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;
}