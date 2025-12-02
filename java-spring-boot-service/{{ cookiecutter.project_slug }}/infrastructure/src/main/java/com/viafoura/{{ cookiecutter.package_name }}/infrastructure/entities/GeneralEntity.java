package com.viafoura.{{ cookiecutter.package_name }}.infrastructure.entities;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@MappedSuperclass
public abstract class GeneralEntity {

  @Id
  @Column(name = "id", nullable = false, updatable = false, columnDefinition = "CHAR(36)")
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type = "uuid-char")
  UUID id = UUID.randomUUID();

  @Column(name = "created_at", nullable = false)
  @CreatedDate
  Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  @LastModifiedDate
  Instant updatedAt = Instant.now();

  @PrePersist
  protected void onCreate() {
    setCreatedAt(Instant.now());
    setUpdatedAt(Instant.now());
  }

  @PreUpdate
  protected void onUpdate() {
    setUpdatedAt(Instant.now());
  }
}
