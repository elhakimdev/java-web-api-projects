package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString
@Embeddable
public class EmbeddedTimeStamp {
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = true)
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  @Column(name = "restored_at", nullable = true)
  private LocalDateTime restoredAt;
}
