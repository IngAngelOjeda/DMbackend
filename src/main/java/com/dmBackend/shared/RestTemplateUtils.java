package com.dmBackend.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateUtils {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateUtils.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<T> sendRequest(
            String url,
            HttpMethod method,
            Object body,
            MediaType contentType,
            String bearerToken,
            Class<T> responseType
    ) {
        try {
            HttpHeaders headers = new HttpHeaders();

            if (contentType != null) {
                headers.setContentType(contentType);
            }

            if (bearerToken != null && !bearerToken.isBlank()) {
                headers.setBearerAuth(bearerToken);
            }

            HttpEntity<Object> entity =
                    (body != null) ? new HttpEntity<>(body, headers) : new HttpEntity<>(headers);

            // === LOG DE LA PETICIÓN ===
            if (log.isInfoEnabled()) {
                log.info("=== REST REQUEST ===");
                log.info("URL: {}", url);
                log.info("Método: {}", method);
                log.info("Headers: {}", headers);
                log.info("Body: {}", body != null ? body : "(vacío)");
            }

            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);

            // === LOG DE LA RESPUESTA ===
            if (log.isInfoEnabled()) {
                log.info("=== REST RESPONSE ===");
                log.info("Código de estado: {}", response.getStatusCode());
                log.info("Cuerpo: {}", response.hasBody() ? response.getBody() : "(sin contenido)");
            }

            return response;

        } catch (HttpClientErrorException e) {
            log.error("Error HTTP: {}", e.getStatusCode());
            log.error("Respuesta del servidor: {}", e.getResponseBodyAsString());
            throw new RuntimeException(
                    "Error en la solicitud HTTP: "
                            + e.getStatusCode()
                            + " - "
                            + e.getResponseBodyAsString());
        }
    }

    public <T> T postForm(String url, Map<String, String> formData, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        StringBuilder body = new StringBuilder();
        formData.forEach((key, value) -> body.append(key).append("=").append(value).append("&"));
        if (body.length() > 0) body.setLength(body.length() - 1);

        // === LOG DE LA PETICIÓN FORM ===
        if (log.isInfoEnabled()) {
            log.info("=== REST FORM REQUEST ===");
            log.info("URL: {}", url);
            log.info("Método: POST");
            log.info("Headers: {}", headers);
            log.info("Body: {}", body);
        }

        HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, entity, responseType);

        // === LOG DE LA RESPUESTA ===
        if (log.isInfoEnabled()) {
            log.info("=== REST RESPONSE ===");
            log.info("Código de estado: {}", response.getStatusCode());
            log.info("Cuerpo: {}", response.hasBody() ? response.getBody() : "(sin contenido)");
        }

        return response.getBody();
    }
}
