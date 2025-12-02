package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.adapters.persistence;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource.Resource;
import com.viafoura.{{ cookiecutter.package_name }}.core.ports.outgoing.ResourceRepositoryPort;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.mappers.ResourceEntityMapper;
import com.viafoura.{{ cookiecutter.package_name }}.infrastructure.repositories.ResourceMySQLJPARepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResourceRepositoryAdapter implements ResourceRepositoryPort {

  final ResourceMySQLJPARepository resourceMySQLJPARepository;
  final ResourceEntityMapper resourceEntityMapper;

  @Override
  public Flux<Resource> getResources() {
    return Flux.fromIterable(
        resourceMySQLJPARepository.findAll().stream()
            .map(resourceEntityMapper::resourceEntityToResource)
            .collect(Collectors.toList()));
  }

  @Override
  public List<Resource> getResource() {
    return resourceMySQLJPARepository.findAll().stream()
        .map(resourceEntityMapper::resourceEntityToResource)
        .collect(Collectors.toList());
  }

  @Override
  public Mono<Resource> save(Resource resource) {
    var savedEntity =
        resourceMySQLJPARepository.save(resourceEntityMapper.resourceToResourceEntity(resource));
    return Mono.just(resourceEntityMapper.resourceEntityToResource(savedEntity));
  }
}

