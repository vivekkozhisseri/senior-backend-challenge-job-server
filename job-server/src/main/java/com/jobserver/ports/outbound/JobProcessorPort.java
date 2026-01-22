package com.jobserver.ports.outbound;

import java.util.UUID;

/**
 * Outbound port for triggering async job processing.
 * This is a driven port - implemented by the async adapter.
 */
public interface JobProcessorPort {

    /**
     * Triggers asynchronous processing of a job.
     * This method must return immediately without blocking.
     * 
     * @param jobId the ID of the job to process
     */
    void triggerProcessing(UUID jobId);
}

