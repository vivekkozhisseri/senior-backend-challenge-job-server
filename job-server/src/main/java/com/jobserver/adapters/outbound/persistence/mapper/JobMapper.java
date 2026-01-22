package com.jobserver.adapters.outbound.persistence.mapper;

import com.jobserver.adapters.outbound.persistence.entity.JobJpaEntity;
import com.jobserver.domain.entity.Job;

public final class JobMapper {

    private JobMapper() {}

    public static JobJpaEntity toJpaEntity(Job job) {
        return new JobJpaEntity(
                job.getId(),
                job.getUserId(),
                job.getProjectId(),
                job.getStatus(),
                job.getRequestPayload(),
                job.getResultPayload(),
                job.getErrorMessage(),
                job.getCreatedAt(),
                job.getUpdatedAt()
        );
    }

    public static Job toDomainEntity(JobJpaEntity entity) {
        return Job.reconstitute(
                entity.getId(),
                entity.getUserId(),
                entity.getProjectId(),
                entity.getStatus(),
                entity.getRequestPayload(),
                entity.getResultPayload(),
                entity.getErrorMessage(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
