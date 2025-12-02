package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.adapters.persistence.fixtures;

import com.github.javafaker.Faker;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.experimental.UtilityClass;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.entities.ResourceEntity;

@UtilityClass
public class ResourceRepositoryAdapterTestFixtures {

  private static final Faker FAKER = new Faker();


  public static List<ResourceEntity> createResourceEntityList() {
    return List.of(createResourceEntity(), createResourceEntity(), createResourceEntity());
  }

  public static ResourceEntity createResourceEntity() {
    var entity = new ResourceEntity();
    entity.setPropertyOne(FAKER.zelda().character());
    entity.setPropertyTwo(FAKER.superhero().name());
    entity.setCreatedAt(Instant.now());
    entity.setUpdatedAt(Instant.now());
    entity.setId(UUID.randomUUID());
    return entity;
  }
}
