package com.dmBackend.infra.security;

import com.dmBackend.infra.exception.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.time.LocalDateTime;

@Configuration
public class SecurityErrorHandlers {
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper mapper) {
        return (request, response, ex) -> {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getOutputStream(),
                    ApiError.builder()
                            .status(401)
                            .error("Unauthorized")
                            .message("No autenticado.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper mapper) {
        return (request, response, ex) -> {
            response.setStatus(403);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getOutputStream(),
                    ApiError.builder()
                            .status(403)
                            .error("Forbidden")
                            .message("No tenés permisos para realizar esta acción.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        };
    }
}
