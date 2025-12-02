package com.viafoura.{{ cookiecutter.package_name }}.core.ports.incoming;

import com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResourceUseCase {
  Flux<Resource> getResources();
  Mono<Resource> saveResource(Resource resource);
}
