package com.jobserver.ports.outbound;

import java.util.UUID;

/**
 * Outbound port for external job processing service.
 * This is a driven port - implemented by the HTTP adapter.
 */
public interface ExternalProcessorPort {

    /**
     * Result of processing a job externally.
     */
    record ProcessingResult(boolean success, String resultPayload, String errorMessage) {
        
        public static ProcessingResult success(String payload) {
            return new ProcessingResult(true, payload, null);
        }
        
        public static ProcessingResult failure(String errorMessage) {
            return new ProcessingResult(false, null, errorMessage);
        }
    }

    /**
     * Calls the external service to process a job.
     * This is a blocking call that should be executed asynchronously.
     * 
     * @param jobId the ID of the job to process
     * @return the result of the external processing
     */
    ProcessingResult process(UUID jobId);
}

