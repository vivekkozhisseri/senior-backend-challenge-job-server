package com.jobserver.domain.exception;

import com.jobserver.domain.enums.JobStatus;

/**
 * Thrown when an invalid state transition is attempted on a Job.
 */
public class InvalidJobStateException extends RuntimeException {

    public InvalidJobStateException(JobStatus currentStatus, JobStatus targetStatus) {
        super(String.format("Invalid job state transition from %s to %s", 
                currentStatus, targetStatus));
    }

    public InvalidJobStateException(String message) {
        super(message);
    }
}

