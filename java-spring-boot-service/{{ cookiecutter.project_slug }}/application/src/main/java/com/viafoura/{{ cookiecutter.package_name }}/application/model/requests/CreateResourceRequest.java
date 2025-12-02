package com.viafoura.{{ cookiecutter.package_name }}.application.model.requests;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateResourceRequest {
  @NotBlank String propertyOne;
  @NotBlank String propertyTwo;
}

