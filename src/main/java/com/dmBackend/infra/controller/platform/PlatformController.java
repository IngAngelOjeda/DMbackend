package com.dmBackend.infra.controller.platform;

import com.dmBackend.app.dto.platform.PlatformRequestDTO;
import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.app.dto.platform.PlatformUpdateRequestDTO;
import com.dmBackend.app.useCase.platform.*;
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
@RequestMapping("/platforms")
@RequiredArgsConstructor
public class PlatformController {

    private final CreatePlatformUseCase createPlatformUseCase;
    private final GetAllPlatformsUseCase getAllPlatformsUseCase;
    private final GetPlatformByIdUseCase getPlatformByIdUseCase;
    private final UpdatePlatformUseCase updatePlatformUseCase;
    private final DeletePlatformUseCase deletePlatformUseCase;

    @PostMapping
    public ResponseEntity<PlatformResponseDTO> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody @Valid PlatformRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createPlatformUseCase.execute(jwt.getSubject(), request));
    }

    @GetMapping
    public ResponseEntity<Page<PlatformResponseDTO>> getAll(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {
        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "asc";
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        return ResponseEntity.ok(getAllPlatformsUseCase.execute(jwt.getSubject(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> getById(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        return ResponseEntity.ok(getPlatformByIdUseCase.execute(jwt.getSubject(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> update(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id,
            @RequestBody @Valid PlatformUpdateRequestDTO request) {
        return ResponseEntity.ok(updatePlatformUseCase.execute(jwt.getSubject(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {
        deletePlatformUseCase.execute(jwt.getSubject(), id);
        return ResponseEntity.noContent().build();
    }
}