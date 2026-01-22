package com.jobserver.domain.enums;

/**
 * Represents the lifecycle status of a Job.
 * Valid transitions:
 * - PENDING → PROCESSING
 * - PROCESSING → COMPLETED
 * - PROCESSING → FAILED
 */
public enum JobStatus {
    PENDING,
    PROCESSING,
    COMPLETED,
    FAILED;

    /**
     * Checks if transitioning to the given status is valid from this status.
     */
    public boolean canTransitionTo(JobStatus target) {
        return switch (this) {
            case PENDING -> target == PROCESSING;
            case PROCESSING -> target == COMPLETED || target == FAILED;
            case COMPLETED, FAILED -> false; // Terminal states
        };
    }
}

