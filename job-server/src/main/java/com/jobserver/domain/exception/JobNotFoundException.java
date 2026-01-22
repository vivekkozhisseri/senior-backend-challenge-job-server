package com.jobserver.domain.exception;

import java.util.UUID;

/**
 * Thrown when a requested Job cannot be found.
 */
public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(UUID jobId) {
        super(String.format("Job not found with id: %s", jobId));
    }
}

