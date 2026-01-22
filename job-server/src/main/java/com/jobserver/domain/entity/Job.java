package com.jobserver.domain.entity;

import com.jobserver.domain.enums.JobStatus;
import com.jobserver.domain.exception.InvalidJobStateException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Job {

    private final UUID id;
    private final UUID userId;
    private final UUID projectId;
    private JobStatus status;
    private final String requestPayload;
    private String resultPayload;
    private String errorMessage;
    private final Instant createdAt;
    private Instant updatedAt;

    public static Job create(UUID userId, UUID projectId, String requestPayload) {
        Objects.requireNonNull(userId, "userId is required");
        return new Job(
                UUID.randomUUID(),
                userId,
                projectId,
                JobStatus.PENDING,
                requestPayload,
                null,
                null,
                Instant.now(),
                Instant.now()
        );
    }

    public static Job reconstitute(
            UUID id, UUID userId, UUID projectId, JobStatus status,
            String requestPayload, String resultPayload, String errorMessage,
            Instant createdAt, Instant updatedAt) {
        return new Job(id, userId, projectId, status, requestPayload, resultPayload, errorMessage, createdAt, updatedAt);
    }

    private Job(UUID id, UUID userId, UUID projectId, JobStatus status,
                String requestPayload, String resultPayload, String errorMessage,
                Instant createdAt, Instant updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.projectId = projectId;
        this.status = Objects.requireNonNull(status);
        this.requestPayload = requestPayload;
        this.resultPayload = resultPayload;
        this.errorMessage = errorMessage;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public void startProcessing() {
        transitionTo(JobStatus.PROCESSING);
    }

    public void complete(String result) {
        transitionTo(JobStatus.COMPLETED);
        this.resultPayload = result;
    }

    public void fail(String errorMessage) {
        transitionTo(JobStatus.FAILED);
        this.errorMessage = errorMessage;
    }

    private void transitionTo(JobStatus targetStatus) {
        if (!this.status.canTransitionTo(targetStatus)) {
            throw new InvalidJobStateException(this.status, targetStatus);
        }
        this.status = targetStatus;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getProjectId() { return projectId; }
    public JobStatus getStatus() { return status; }
    public String getRequestPayload() { return requestPayload; }
    public String getResultPayload() { return resultPayload; }
    public String getErrorMessage() { return errorMessage; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public boolean isTerminal() { return status == JobStatus.COMPLETED || status == JobStatus.FAILED; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Job) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
