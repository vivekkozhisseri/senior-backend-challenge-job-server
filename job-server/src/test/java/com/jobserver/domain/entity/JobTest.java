package com.jobserver.domain.entity;

import com.jobserver.domain.enums.JobStatus;
import com.jobserver.domain.exception.InvalidJobStateException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID PROJECT_ID = UUID.randomUUID();

    @Test
    void shouldCreateJobWithPendingStatus() {
        Job job = Job.create(USER_ID, PROJECT_ID, "payload");
        
        assertNotNull(job.getId());
        assertEquals(USER_ID, job.getUserId());
        assertEquals(PROJECT_ID, job.getProjectId());
        assertEquals(JobStatus.PENDING, job.getStatus());
    }

    @Test
    void shouldRequireUserId() {
        assertThrows(NullPointerException.class, () -> Job.create(null, PROJECT_ID, "payload"));
    }

    @Test
    void shouldAllowNullProjectId() {
        Job job = Job.create(USER_ID, null, "payload");
        assertNull(job.getProjectId());
    }

    @Test
    void shouldTransitionFromPendingToProcessing() {
        Job job = Job.create(USER_ID, null, "payload");
        job.startProcessing();
        assertEquals(JobStatus.PROCESSING, job.getStatus());
    }

    @Test
    void shouldTransitionFromProcessingToCompleted() {
        Job job = Job.create(USER_ID, null, "payload");
        job.startProcessing();
        job.complete("result");
        assertEquals(JobStatus.COMPLETED, job.getStatus());
        assertEquals("result", job.getResultPayload());
    }

    @Test
    void shouldTransitionFromProcessingToFailed() {
        Job job = Job.create(USER_ID, null, "payload");
        job.startProcessing();
        job.fail("error");
        assertEquals(JobStatus.FAILED, job.getStatus());
        assertEquals("error", job.getErrorMessage());
    }

    @Test
    void shouldNotAllowInvalidTransition() {
        Job job = Job.create(USER_ID, null, "payload");
        assertThrows(InvalidJobStateException.class, () -> job.complete("result"));
    }
}
