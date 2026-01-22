package com.jobserver.adapters.outbound.async;

import com.jobserver.application.service.JobProcessingService;
import com.jobserver.ports.outbound.JobProcessorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adapter for triggering async job processing.
 * Uses Spring's @Async to execute processing in a separate thread pool.
 */
@Component
public class AsyncJobProcessorAdapter implements JobProcessorPort {

    private static final Logger log = LoggerFactory.getLogger(AsyncJobProcessorAdapter.class);

    private final JobProcessingService jobProcessingService;

    public AsyncJobProcessorAdapter(JobProcessingService jobProcessingService) {
        this.jobProcessingService = jobProcessingService;
    }

    @Override
    @Async("jobProcessorExecutor")
    public void triggerProcessing(UUID jobId) {
        log.debug("Async processing triggered for job: {} on thread: {}", 
                jobId, Thread.currentThread().getName());
        
        jobProcessingService.processJob(jobId);
    }
}

