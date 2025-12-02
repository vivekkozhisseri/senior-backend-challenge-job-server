package com.viafoura.{{ cookiecutter.package_name }}.core.ports.outgoing;

import java.util.List;
import com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResourceRepositoryPort {

  Flux<Resource> getResources();

  List<Resource> getResource();

  Mono<Resource> save(Resource resource);
}
