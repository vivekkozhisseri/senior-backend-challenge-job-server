package com.jobserver.ports.inbound;

import com.jobserver.domain.entity.Job;

import java.util.UUID;

public interface JobUseCases {
    Job submitJob(UUID userId, UUID projectId, String requestPayload);
    Job getJob(UUID jobId);
}
