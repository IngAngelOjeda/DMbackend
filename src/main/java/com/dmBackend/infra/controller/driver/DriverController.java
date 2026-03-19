package com.dmBackend.infra.controller.driver;

import com.dmBackend.app.dto.driver.DriverRequestDTO;
import com.dmBackend.app.dto.driver.DriverResponseDTO;
import com.dmBackend.app.dto.driver.DriverUpdateRequestDTO;
import com.dmBackend.app.useCase.driver.*;
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
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final CreateDriverUseCase createDriverUseCase;
    private final GetAllDriversUseCase getAllDriversUseCase;
    private final GetDriverByIdUseCase getDriverByIdUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final DeleteDriverUseCase deleteDriverUseCase;

    @PostMapping
    public ResponseEntity<DriverResponseDTO> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid DriverRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createDriverUseCase.execute(jwt.getSubject(), request));
    }

    @GetMapping
    public ResponseEntity<Page<DriverResponseDTO>> getAll(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName,asc") String[] sort) {
        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "asc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return ResponseEntity.ok(getAllDriversUseCase.execute(jwt.getSubject(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> getById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        return ResponseEntity.ok(getDriverByIdUseCase.execute(jwt.getSubject(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id,
            @RequestBody @Valid DriverUpdateRequestDTO request) {
        return ResponseEntity.ok(updateDriverUseCase.execute(jwt.getSubject(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        deleteDriverUseCase.execute(jwt.getSubject(), id);
        return ResponseEntity.noContent().build();
    }
}