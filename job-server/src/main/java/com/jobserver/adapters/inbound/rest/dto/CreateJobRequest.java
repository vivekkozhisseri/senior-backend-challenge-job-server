package com.jobserver.adapters.inbound.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateJobRequest(
        @NotNull(message = "userId is required")
        UUID userId,
        UUID projectId,
        @NotBlank(message = "payload is required")
        String payload
) {}
