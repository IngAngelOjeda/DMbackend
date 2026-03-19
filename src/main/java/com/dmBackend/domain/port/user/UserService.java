package com.dmBackend.domain.port.user;

import com.dmBackend.app.dto.user.UserGetResponseDTO;
import com.dmBackend.app.dto.user.UserPageResponseDTO;
import com.dmBackend.app.dto.user.UserRequestDTO;
import com.dmBackend.app.dto.user.UserResponseDTO;
import com.dmBackend.app.dto.user.UserStatusUpdateDTO;
import com.dmBackend.app.dto.user.UserUpdateRequestDTO;
import com.dmBackend.app.dto.user.UserUpdateResponseDTO;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO create(UserRequestDTO request, String keycloakId);

    UserPageResponseDTO getAll(String search, Pageable pageable);

    UserGetResponseDTO getById(String keycloakId);

    UserUpdateResponseDTO update(String keycloakId, UserUpdateRequestDTO request);

    UserUpdateResponseDTO updateStatus(String keycloakId, UserStatusUpdateDTO request);
}

