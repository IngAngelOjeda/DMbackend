package com.dmBackend.domain.port.platform;

import com.dmBackend.app.dto.platform.PlatformRequestDTO;
import com.dmBackend.app.dto.platform.PlatformResponseDTO;
import com.dmBackend.app.dto.platform.PlatformUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlatformService {

    PlatformResponseDTO create(String userId, PlatformRequestDTO request);

    Page<PlatformResponseDTO> getAll(String userId, Pageable pageable);

    PlatformResponseDTO getById(String userId, Long platformId);

    PlatformResponseDTO update(String userId, Long platformId, PlatformUpdateRequestDTO request);

    void delete(String userId, Long platformId);
}