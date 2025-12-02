package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.adapters.persistence;

import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.adapters.persistence.fixtures.ResourceRepositoryAdapterTestFixtures;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.mappers.ResourceEntityMapper;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.mappers.ResourceEntityMapperImpl;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.repositories.ResourceMySQLJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {ResourceEntityMapperImpl.class})
public class ResourceRepositoryAdapterTest {

  private ResourceRepositoryAdapter resourceRepositoryAdapter;

  @Mock
  private ResourceMySQLJPARepository resourceMySQLJPARepository;

  @Autowired
  private ResourceEntityMapper resourceEntityMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    resourceRepositoryAdapter =
        new ResourceRepositoryAdapter(resourceMySQLJPARepository, resourceEntityMapper);
  }

  @Test
  void when_get_flux_all_resources_then_return_list() {
    var entities = ResourceRepositoryAdapterTestFixtures.createResourceEntityList();
    var resources =
        entities.stream()
            .map(resourceEntityMapper::resourceEntityToResource)
            .collect(Collectors.toList());
    when(resourceMySQLJPARepository.findAll()).thenReturn(entities);
    var result = resourceRepositoryAdapter.getResources();
    StepVerifier.create(result).expectNextSequence(resources).expectComplete();
  }

  @Test
  void when_get_flux_all_resources_then_return_empty_list() {
    when(resourceMySQLJPARepository.findAll()).thenReturn(Collections.emptyList());
    var result = resourceRepositoryAdapter.getResources();
    StepVerifier.create(result).expectNextCount(0).verifyComplete();
  }

  @Test
  void when_get_all_resources_then_return_list() {
    var entities = ResourceRepositoryAdapterTestFixtures.createResourceEntityList();
    when(resourceMySQLJPARepository.findAll()).thenReturn(entities);
    var result = resourceRepositoryAdapter.getResource();
    assertFalse(result.isEmpty());
  }

  @Test
  void when_save_resources_then_success() {
    var resourceEntity = ResourceRepositoryAdapterTestFixtures.createResourceEntity();
    var resource = resourceEntityMapper.resourceEntityToResource(resourceEntity);
    when(resourceMySQLJPARepository.save(any())).thenReturn(resourceEntity);
    var result = resourceRepositoryAdapter.save(resource);
    StepVerifier.create(result).expectNext(resource).verifyComplete();
  }
}
