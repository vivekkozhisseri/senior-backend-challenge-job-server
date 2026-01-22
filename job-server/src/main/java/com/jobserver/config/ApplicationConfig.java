package com.jobserver.config;

import com.jobserver.application.service.JobProcessingService;
import com.jobserver.application.service.JobService;
import com.jobserver.ports.outbound.ExternalProcessorPort;
import com.jobserver.ports.outbound.JobProcessorPort;
import com.jobserver.ports.outbound.JobRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration - wires up use cases and services.
 * This is where we compose the hexagonal architecture layers.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Creates the main job service with its dependencies.
     */
    @Bean
    public JobService jobService(JobRepositoryPort jobRepository, JobProcessorPort jobProcessor) {
        return new JobService(jobRepository, jobProcessor);
    }

    /**
     * Creates the job processing service for async execution.
     */
    @Bean
    public JobProcessingService jobProcessingService(
            JobRepositoryPort jobRepository,
            ExternalProcessorPort externalProcessor) {
        return new JobProcessingService(jobRepository, externalProcessor);
    }
}

