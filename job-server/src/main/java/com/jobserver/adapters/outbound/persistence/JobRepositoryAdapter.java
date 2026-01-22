package com.jobserver.adapters.outbound.persistence;

import com.jobserver.adapters.outbound.persistence.mapper.JobMapper;
import com.jobserver.adapters.outbound.persistence.repository.JpaJobRepository;
import com.jobserver.domain.entity.Job;
import com.jobserver.ports.outbound.JobRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Adapter implementing the job repository port using Spring Data JPA.
 * Translates between domain entities and JPA entities.
 */
@Component
public class JobRepositoryAdapter implements JobRepositoryPort {

    private final JpaJobRepository jpaRepository;

    public JobRepositoryAdapter(JpaJobRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public Job save(Job job) {
        var jpaEntity = JobMapper.toJpaEntity(job);
        var savedEntity = jpaRepository.save(jpaEntity);
        return JobMapper.toDomainEntity(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Job> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(JobMapper::toDomainEntity);
    }
}

