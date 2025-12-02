package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.repositories;

import java.util.UUID;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceMySQLJPARepository extends JpaRepository<ResourceEntity, UUID> {}
