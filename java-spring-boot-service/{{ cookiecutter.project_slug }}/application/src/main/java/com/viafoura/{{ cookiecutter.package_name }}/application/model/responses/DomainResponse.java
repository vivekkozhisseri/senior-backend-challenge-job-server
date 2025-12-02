package com.viafoura.{{ cookiecutter.package_name }}.application.model.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Abstract class to represent domain model generic attributes (id, createdAt, updatedAt) If your
 * response object doesn't need those attributes don't inherit from this class
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class DomainResponse {
  @Schema(description = "Domain unique identifier")
  UUID id;

  @Schema(description = "Date when domain was created")
  Instant createdAt;

  @Schema(description = "Date when domain was updated")
  Instant updatedAt;
}

