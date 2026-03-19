package com.dmBackend.infra.controller.vehicle;

import com.dmBackend.app.dto.vehicle.VehicleRequestDTO;
import com.dmBackend.app.dto.vehicle.VehicleResponseDTO;
import com.dmBackend.app.dto.vehicle.VehicleUpdateRequestDTO;
import com.dmBackend.app.useCase.vehicle.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final CreateVehicleUseCase createVehicleUseCase;
    private final GetAllVehiclesUseCase getAllVehiclesUseCase;
    private final GetVehicleByIdUseCase getVehicleByIdUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid VehicleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createVehicleUseCase.execute(jwt.getSubject(), request));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDTO>> getAll(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "desc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return ResponseEntity.ok(getAllVehiclesUseCase.execute(jwt.getSubject(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        return ResponseEntity.ok(getVehicleByIdUseCase.execute(jwt.getSubject(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id,
            @RequestBody @Valid VehicleUpdateRequestDTO request) {
        return ResponseEntity.ok(updateVehicleUseCase.execute(jwt.getSubject(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        deleteVehicleUseCase.execute(jwt.getSubject(), id);
        return ResponseEntity.noContent().build();
    }
}