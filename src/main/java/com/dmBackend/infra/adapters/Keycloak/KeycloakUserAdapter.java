package com.dmBackend.infra.adapters.Keycloak;

import com.dmBackend.app.dto.user.UserRequestDTO;
import com.dmBackend.domain.model.generic.RoleAPI;
import com.dmBackend.infra.exception.BusinessException;
import com.dmBackend.shared.RestTemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakUserAdapter {

    private final RestTemplateUtils restTemplateUtils;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    /**
     * Creates a user in Keycloak and returns the Keycloak user ID (subject).
     */
    public String createUser(UserRequestDTO request) {
        String adminToken = getAdminToken();

        Map<String, Object> credential = Map.of(
                "type", "password",
                "value", request.getPassword(),
                "temporary", false
        );

        Map<String, Object> keycloakUser = Map.of(
                "username", request.getUsername(),
                "email", request.getEmail(),
                "firstName", request.getFirstName(),
                "lastName", request.getLastName(),
                "enabled", true,
                "credentials", List.of(credential)
        );

        String usersUrl = authServerUrl + "/admin/realms/" + realm + "/users";

        ResponseEntity<Void> response = restTemplateUtils.sendRequest(
                usersUrl,
                HttpMethod.POST,
                keycloakUser,
                MediaType.APPLICATION_JSON,
                adminToken,
                Void.class
        );

        String location = response.getHeaders().getFirst("Location");
        if (location == null) {
            throw new BusinessException("Keycloak did not return a user location header");
        }

        String keycloakId = location.substring(location.lastIndexOf('/') + 1);
        log.info("User created in Keycloak with id: {}", keycloakId);

        assignClientRole(adminToken, keycloakId, request.getRole());

        return keycloakId;
    }

    /**
     * Enables or disables a user in Keycloak.
     */
    public void updateUserStatus(String keycloakId, boolean enabled) {
        String adminToken = getAdminToken();
        String userUrl = authServerUrl + "/admin/realms/" + realm + "/users/" + keycloakId;

        restTemplateUtils.sendRequest(
                userUrl,
                HttpMethod.PUT,
                Map.of("enabled", enabled),
                MediaType.APPLICATION_JSON,
                adminToken,
                Void.class
        );
    }

    /**
     * Updates user attributes in Keycloak.
     */
    public void updateUser(String keycloakId, String email, String firstName, String lastName) {
        String adminToken = getAdminToken();
        String userUrl = authServerUrl + "/admin/realms/" + realm + "/users/" + keycloakId;

        restTemplateUtils.sendRequest(
                userUrl,
                HttpMethod.PUT,
                Map.of(
                        "email", email,
                        "firstName", firstName,
                        "lastName", lastName
                ),
                MediaType.APPLICATION_JSON,
                adminToken,
                Void.class
        );
    }

    @SuppressWarnings("unchecked")
    private void assignClientRole(String adminToken, String keycloakUserId, RoleAPI role) {
        // 1. Resolve the internal Keycloak UUID for our client
        String clientsUrl = authServerUrl + "/admin/realms/" + realm + "/clients?clientId=" + clientId;
        ResponseEntity<List> clientsResponse = restTemplateUtils.sendRequest(
                clientsUrl, HttpMethod.GET, null, null, adminToken, List.class);

        List<Map<String, Object>> clients = (List<Map<String, Object>>) clientsResponse.getBody();
        if (clients == null || clients.isEmpty()) {
            throw new BusinessException("Keycloak client not found: " + clientId);
        }
        String clientUuid = (String) clients.get(0).get("id");

        // 2. Fetch the role representation by name
        String roleName = role.name();
        String roleUrl = authServerUrl + "/admin/realms/" + realm + "/clients/" + clientUuid + "/roles/" + roleName;
        ResponseEntity<Map> roleResponse = restTemplateUtils.sendRequest(
                roleUrl, HttpMethod.GET, null, null, adminToken, Map.class);

        Map<String, Object> roleRepresentation = roleResponse.getBody();
        if (roleRepresentation == null || !roleRepresentation.containsKey("id")) {
            throw new BusinessException("Role not found in Keycloak client '" + clientId + "': " + roleName);
        }

        // 3. Assign the client role to the user
        String assignUrl = authServerUrl + "/admin/realms/" + realm
                + "/users/" + keycloakUserId + "/role-mappings/clients/" + clientUuid;
        restTemplateUtils.sendRequest(
                assignUrl, HttpMethod.POST,
                List.of(Map.of("id", roleRepresentation.get("id"), "name", roleRepresentation.get("name"))),
                MediaType.APPLICATION_JSON, adminToken, Void.class);

        log.info("Role '{}' assigned to user '{}' on client '{}'", roleName, keycloakUserId, clientId);
    }

    @SuppressWarnings("unchecked")
    private String getAdminToken() {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        Map<String, String> formData = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "grant_type", "client_credentials"
        );

        Map<String, Object> tokenResponse = restTemplateUtils.postForm(tokenUrl, formData, Map.class);

        if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
            throw new BusinessException("Failed to obtain Keycloak admin token");
        }

        return (String) tokenResponse.get("access_token");
    }
}