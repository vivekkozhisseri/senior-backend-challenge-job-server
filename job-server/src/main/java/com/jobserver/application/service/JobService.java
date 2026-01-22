package com.jobserver.application.service;

import com.jobserver.domain.entity.Job;
import com.jobserver.domain.exception.JobNotFoundException;
import com.jobserver.ports.inbound.JobUseCases;
import com.jobserver.ports.outbound.JobProcessorPort;
import com.jobserver.ports.outbound.JobRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class JobService implements JobUseCases {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    private final JobRepositoryPort jobRepository;
    private final JobProcessorPort jobProcessor;

    public JobService(JobRepositoryPort jobRepository, JobProcessorPort jobProcessor) {
        this.jobRepository = jobRepository;
        this.jobProcessor = jobProcessor;
    }

    @Override
    public Job submitJob(UUID userId, UUID projectId, String requestPayload) {
        log.info("Submitting job for user: {}", userId);
        
        Job job = Job.create(userId, projectId, requestPayload);
        Job savedJob = jobRepository.save(job);
        
        jobProcessor.triggerProcessing(savedJob.getId());
        log.info("Job created: {}", savedJob.getId());
        
        return savedJob;
    }

    @Override
    public Job getJob(UUID jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(jobId));
    }
}
