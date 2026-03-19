package com.dmBackend.infra.adapters.user;

import com.dmBackend.app.dto.user.*;
import com.dmBackend.domain.model.UserEntity;
import com.dmBackend.domain.port.user.UserService;
import com.dmBackend.infra.exception.BusinessException;
import com.dmBackend.infra.exception.ResourceNotFoundException;
import com.dmBackend.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO request, String keycloakId) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("A user with email '" + request.getEmail() + "' already exists");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("A user with username '" + request.getUsername() + "' already exists");
        }
        if (userRepository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new BusinessException("A user with document number '" + request.getDocumentNumber() + "' already exists");
        }

        UserEntity user = UserEntity.builder()
                .keycloakId(keycloakId)
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .documentNumber(request.getDocumentNumber())
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return new UserResponseDTO(user.getUsername(), "User created successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public UserPageResponseDTO getAll(String search, Pageable pageable) {
        Page<UserEntity> page = userRepository.findBySearch(search, pageable);

        List<UserGetResponseDTO> users = page.getContent().stream()
                .map(this::toGetResponse)
                .toList();

        return new UserPageResponseDTO(users, page.getNumber(), page.getTotalPages(), page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public UserGetResponseDTO getById(String keycloakId) {
        UserEntity user = findByKeycloakId(keycloakId);
        return toGetResponse(user);
    }

    @Override
    @Transactional
    public UserUpdateResponseDTO update(String keycloakId, UserUpdateRequestDTO request) {
        UserEntity user = findByKeycloakId(keycloakId);

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());

        userRepository.save(user);
        return new UserUpdateResponseDTO("User updated successfully");
    }

    @Override
    @Transactional
    public UserUpdateResponseDTO updateStatus(String keycloakId, UserStatusUpdateDTO request) {
        UserEntity user = findByKeycloakId(keycloakId);

        user.setActive(request.getStatus());
        userRepository.save(user);

        String statusMessage = request.getStatus() ? "activated" : "deactivated";
        return new UserUpdateResponseDTO("User " + statusMessage + " successfully");
    }

    private UserEntity findByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("User with keycloakId '" + keycloakId + "' not found"));
    }

    private UserGetResponseDTO toGetResponse(UserEntity u) {
        return new UserGetResponseDTO(
                u.getKeycloakId(),
                u.getUsername(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getDocumentNumber(),
                u.isActive()
        );
    }
}