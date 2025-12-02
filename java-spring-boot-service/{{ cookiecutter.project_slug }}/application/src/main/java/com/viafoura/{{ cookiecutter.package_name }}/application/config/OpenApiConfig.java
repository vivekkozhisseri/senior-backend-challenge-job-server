package com.viafoura.{{ cookiecutter.package_name }}.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(getInfo()).externalDocs(getExternalDocs());
  }

  private ExternalDocumentation getExternalDocs() {
    return new ExternalDocumentation()
        .description("Github")
        .url("https://github.com/viafoura/{{ cookiecutter.package_name }}");
  }

  private Info getInfo() {
    return new Info()
        .title("{{ cookiecutter.package_name }}")
        .description("Provide some microservice description")
        .version("0.0.1");
  }
}