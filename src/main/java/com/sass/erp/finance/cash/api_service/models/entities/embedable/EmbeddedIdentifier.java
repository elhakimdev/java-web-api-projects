package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedIdentifier implements Serializable {
  private UUID uuid;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EmbeddedIdentifier that = (EmbeddedIdentifier) o;
    return Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
