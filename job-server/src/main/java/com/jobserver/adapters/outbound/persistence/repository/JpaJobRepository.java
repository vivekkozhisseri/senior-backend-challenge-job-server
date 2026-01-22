package com.jobserver.adapters.outbound.persistence.repository;

import com.jobserver.adapters.outbound.persistence.entity.JobJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for Job entities.
 * This is infrastructure - used only by the adapter.
 */
@Repository
public interface JpaJobRepository extends JpaRepository<JobJpaEntity, UUID> {
}

