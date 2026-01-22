package com.jobserver.ports.outbound;

import com.jobserver.domain.entity.Job;

import java.util.Optional;
import java.util.UUID;

/**
 * Outbound port for job persistence operations.
 * This is a driven port - implemented by the persistence adapter.
 */
public interface JobRepositoryPort {

    /**
     * Saves a job to the persistence store.
     * 
     * @param job the job to save
     * @return the saved job
     */
    Job save(Job job);

    /**
     * Finds a job by its ID.
     * 
     * @param id the job ID
     * @return optional containing the job if found
     */
    Optional<Job> findById(UUID id);
}

