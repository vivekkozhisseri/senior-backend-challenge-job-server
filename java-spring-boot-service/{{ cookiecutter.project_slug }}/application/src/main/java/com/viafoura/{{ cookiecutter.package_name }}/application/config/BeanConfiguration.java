package com.viafoura.{{ cookiecutter.package_name }}.application.config;

import com.viafoura.{{ cookiecutter.package_name }}.core.ports.outgoing.ResourceRepositoryPort;
import com.viafoura.{{ cookiecutter.package_name }}.core.services.ResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
  @Bean
  public ResourceService resourceService(ResourceRepositoryPort resourceRepositoryPort) {
    return new ResourceService(resourceRepositoryPort);
  }
}
