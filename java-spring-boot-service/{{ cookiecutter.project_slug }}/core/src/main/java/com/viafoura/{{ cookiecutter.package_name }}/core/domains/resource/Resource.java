package com.viafoura.{{ cookiecutter.package_name }}.core.domains.resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import com.viafoura.{{ cookiecutter.package_name }}.core.domains.Domain;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Resource extends Domain {
  String propertyOne;
  String propertyTwo;
}
