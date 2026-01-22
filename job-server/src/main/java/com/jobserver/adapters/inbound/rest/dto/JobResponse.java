package com.jobserver.adapters.inbound.rest.dto;

import com.jobserver.domain.entity.Job;
import com.jobserver.domain.enums.JobStatus;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID jobId,
        UUID userId,
        UUID projectId,
        JobStatus status,
        String result,
        String error,
        Instant createdAt,
        Instant updatedAt
) {
    public static JobResponse fromDomain(Job job) {
        return new JobResponse(
                job.getId(),
                job.getUserId(),
                job.getProjectId(),
                job.getStatus(),
                job.getResultPayload(),
                job.getErrorMessage(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }
}
