package com.dmBackend.infra.adapters.platform;

import com.dmBackend.app.dto.platform.PlatformRequestDTO;
import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.app.dto.platform.PlatformUpdateRequestDTO;
import com.dmBackend.domain.model.PlatformEntity;
import com.dmBackend.domain.port.platform.PlatformService;
import com.dmBackend.infra.exception.BusinessException;
import com.dmBackend.infra.exception.ResourceNotFoundException;
import com.dmBackend.infra.repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {

    private final PlatformRepository platformRepository;

    @Override
    @Transactional
    public PlatformResponseDTO create(String userId, PlatformRequestDTO request) {
        if (platformRepository.existsByUserIdAndName(userId, request.getName())) {
            throw new BusinessException("A platform with name '" + request.getName() + "' already exists");
        }

        PlatformEntity platform = PlatformEntity.builder()
                .userId(userId)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return toResponse(platformRepository.save(platform));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlatformResponseDTO> getAll(String userId, Pageable pageable) {
        return platformRepository.findByUserId(userId, pageable).map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PlatformResponseDTO getById(String userId, Long platformId) {
        return toResponse(findOwnedPlatform(userId, platformId));
    }

    @Override
    @Transactional
    public PlatformResponseDTO update(String userId, Long platformId, PlatformUpdateRequestDTO request) {
        PlatformEntity platform = findOwnedPlatform(userId, platformId);

        if (request.getName() != null && !request.getName().equals(platform.getName())) {
            if (platformRepository.existsByUserIdAndName(userId, request.getName())) {
                throw new BusinessException("A platform with name '" + request.getName() + "' already exists");
            }
            platform.setName(request.getName());
        }

        if (request.getDescription() != null) platform.setDescription(request.getDescription());
        if (request.getActive() != null) platform.setActive(request.getActive());

        return toResponse(platformRepository.save(platform));
    }

    @Override
    @Transactional
    public void delete(String userId, Long platformId) {
        PlatformEntity platform = findOwnedPlatform(userId, platformId);
        platform.setActive(false);
        platformRepository.save(platform);
    }

    private PlatformEntity findOwnedPlatform(String userId, Long platformId) {
        return platformRepository.findByIdAndUserId(platformId, userId)
                .orElseThrow(() -> ResourceNotFoundException.of("Platform", platformId));
    }

    private PlatformResponseDTO toResponse(PlatformEntity p) {
        return PlatformResponseDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .active(p.isActive())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}