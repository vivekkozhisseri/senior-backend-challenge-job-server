package com.jobserver.application.service;

import com.jobserver.domain.entity.Job;
import com.jobserver.domain.exception.InvalidJobStateException;
import com.jobserver.domain.exception.JobNotFoundException;
import com.jobserver.ports.outbound.ExternalProcessorPort;
import com.jobserver.ports.outbound.ExternalProcessorPort.ProcessingResult;
import com.jobserver.ports.outbound.JobRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Service responsible for the actual job processing logic.
 * This is called asynchronously by the job processor adapter.
 * 
 * Note: This class has no Spring annotations - configuration is done externally.
 */
public class JobProcessingService {

    private static final Logger log = LoggerFactory.getLogger(JobProcessingService.class);

    private final JobRepositoryPort jobRepository;
    private final ExternalProcessorPort externalProcessor;

    public JobProcessingService(JobRepositoryPort jobRepository, 
                                ExternalProcessorPort externalProcessor) {
        this.jobRepository = jobRepository;
        this.externalProcessor = externalProcessor;
    }

    /**
     * Processes a job by calling the external service.
     * This method is designed to be called asynchronously.
     * 
     * @param jobId the ID of the job to process
     */
    public void processJob(UUID jobId) {
        log.info("Starting processing for job: {}", jobId);
        
        try {
            // Fetch the job
            Job job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new JobNotFoundException(jobId));
            
            // Transition to PROCESSING
            job.startProcessing();
            jobRepository.save(job);
            log.info("Job {} status updated to PROCESSING", jobId);
            
            // Call external service (this is the slow/blocking part)
            ProcessingResult result = externalProcessor.process(jobId);
            
            // Update job based on result
            // Fetch fresh copy to avoid stale data
            job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new JobNotFoundException(jobId));
            
            if (result.success()) {
                job.complete(result.resultPayload());
                log.info("Job {} completed successfully", jobId);
            } else {
                job.fail(result.errorMessage());
                log.warn("Job {} failed: {}", jobId, result.errorMessage());
            }
            
            jobRepository.save(job);
            
        } catch (InvalidJobStateException e) {
            log.error("Invalid state transition for job {}: {}", jobId, e.getMessage());
            // Job is in unexpected state - this is a programming error, log and skip
            
        } catch (JobNotFoundException e) {
            log.error("Job {} not found during processing", jobId);
            // Job was deleted - nothing to do
            
        } catch (Exception e) {
            log.error("Unexpected error processing job {}: {}", jobId, e.getMessage(), e);
            // Try to mark job as failed
            markJobFailed(jobId, "Internal error: " + e.getMessage());
        }
    }

    private void markJobFailed(UUID jobId, String errorMessage) {
        try {
            jobRepository.findById(jobId).ifPresent(job -> {
                if (!job.isTerminal()) {
                    try {
                        // If in PENDING, transition to PROCESSING first
                        if (job.getStatus() == com.jobserver.domain.enums.JobStatus.PENDING) {
                            job.startProcessing();
                        }
                        job.fail(errorMessage);
                        jobRepository.save(job);
                    } catch (InvalidJobStateException e) {
                        log.warn("Could not mark job {} as failed: {}", jobId, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            log.error("Failed to mark job {} as failed: {}", jobId, e.getMessage());
        }
    }
}

