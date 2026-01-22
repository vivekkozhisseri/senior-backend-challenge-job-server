package com.jobserver.adapters.outbound.persistence.entity;

import com.jobserver.domain.enums.JobStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "jobs")
public class JobJpaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "project_id")
    private UUID projectId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status;

    @Column(name = "request_payload", columnDefinition = "TEXT")
    private String requestPayload;

    @Column(name = "result_payload", columnDefinition = "TEXT")
    private String resultPayload;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected JobJpaEntity() {}

    public JobJpaEntity(UUID id, UUID userId, UUID projectId, JobStatus status,
                        String requestPayload, String resultPayload, String errorMessage,
                        Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.projectId = projectId;
        this.status = status;
        this.requestPayload = requestPayload;
        this.resultPayload = resultPayload;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
}
