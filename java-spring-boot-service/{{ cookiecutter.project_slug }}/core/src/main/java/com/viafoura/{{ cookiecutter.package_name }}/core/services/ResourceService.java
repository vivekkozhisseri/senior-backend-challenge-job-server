package com.viafoura.{{ cookiecutter.package_name }}.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource.Resource;
import com.viafoura.{{ cookiecutter.package_name }}.core.ports.incoming.ResourceUseCase;
import com.viafoura.{{ cookiecutter.package_name }}.core.ports.outgoing.ResourceRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ResourceService implements ResourceUseCase {

  final ResourceRepositoryPort resourceRepositoryPort;

  public Flux<Resource> getResources() {
    log.info("Retrieving resources");
    return resourceRepositoryPort.getResources();
  }

  @Override
  public Mono<Resource> saveResource(Resource resource) {
    return resourceRepositoryPort.save(resource);
  }
}
