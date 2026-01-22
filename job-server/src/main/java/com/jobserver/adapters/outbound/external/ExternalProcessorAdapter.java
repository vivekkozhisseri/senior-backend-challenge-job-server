package com.jobserver.adapters.outbound.external;

import com.jobserver.ports.outbound.ExternalProcessorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

/**
 * Adapter for calling the external processing service.
 * Uses WebClient for non-blocking HTTP calls with proper timeout handling.
 */
@Component
public class ExternalProcessorAdapter implements ExternalProcessorPort {

    private static final Logger log = LoggerFactory.getLogger(ExternalProcessorAdapter.class);

    private final WebClient webClient;
    private final String externalServiceUrl;
    private final Duration timeout;

    public ExternalProcessorAdapter(
            WebClient webClient,
            @Value("${job-server.external-service.url}") String externalServiceUrl,
            @Value("${job-server.external-service.timeout-seconds:30}") int timeoutSeconds) {
        this.webClient = webClient;
        this.externalServiceUrl = externalServiceUrl;
        this.timeout = Duration.ofSeconds(timeoutSeconds);
    }

    @Override
    public ProcessingResult process(UUID jobId) {
        log.info("Calling external service for job: {}", jobId);
        
        try {
            String response = webClient.post()
                    .uri(externalServiceUrl + "/process")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Map.of("jobId", jobId.toString()))
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(timeout)
                    .block();
            
            log.info("External service responded for job {}: {}", jobId, response);
            return ProcessingResult.success(response);
            
        } catch (WebClientResponseException e) {
            String error = String.format("External service error: %d - %s", 
                    e.getStatusCode().value(), e.getResponseBodyAsString());
            log.error("External service failed for job {}: {}", jobId, error);
            return ProcessingResult.failure(error);
            
        } catch (Exception e) {
            String error = "External service call failed: " + e.getMessage();
            log.error("External service call failed for job {}: {}", jobId, e.getMessage(), e);
            return ProcessingResult.failure(error);
        }
    }
}

