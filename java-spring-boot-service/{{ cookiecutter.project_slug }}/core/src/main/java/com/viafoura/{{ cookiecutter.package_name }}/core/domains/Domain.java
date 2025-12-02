package com.viafoura.{{ cookiecutter.package_name }}.core.domains;

import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Domain {
  UUID id;
  Instant createdAt;
  Instant updatedAt;
}