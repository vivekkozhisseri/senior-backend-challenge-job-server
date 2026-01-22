package com.jobserver.application.service;

import com.jobserver.domain.entity.Job;
import com.jobserver.domain.enums.JobStatus;
import com.jobserver.domain.exception.JobNotFoundException;
import com.jobserver.ports.outbound.JobProcessorPort;
import com.jobserver.ports.outbound.JobRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepositoryPort jobRepository;
    @Mock
    private JobProcessorPort jobProcessor;

    private JobService jobService;

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID PROJECT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        jobService = new JobService(jobRepository, jobProcessor);
    }

    @Test
    void submitJobShouldCreatePendingJob() {
        when(jobRepository.save(any(Job.class))).thenAnswer(inv -> inv.getArgument(0));

        Job result = jobService.submitJob(USER_ID, PROJECT_ID, "payload");

        assertEquals(JobStatus.PENDING, result.getStatus());
        assertEquals(USER_ID, result.getUserId());
        verify(jobProcessor).triggerProcessing(result.getId());
    }

    @Test
    void getJobShouldReturnJob() {
        Job job = Job.create(USER_ID, null, "payload");
        when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));

        Job result = jobService.getJob(job.getId());

        assertEquals(job.getId(), result.getId());
    }

    @Test
    void getJobShouldThrowWhenNotFound() {
        UUID jobId = UUID.randomUUID();
        when(jobRepository.findById(jobId)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> jobService.getJob(jobId));
    }
}
