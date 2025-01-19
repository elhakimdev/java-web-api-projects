package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@ToString
@Embeddable
public class EmbeddedUUID  implements Serializable {
    private UUID uuid;

    @PrePersist
    public void prePersist() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID(); // Generate UUID if not provided
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Compare reference equality
        if (o == null || getClass() != o.getClass()) return false; // Check for null and same class
        EmbeddedUUID that = (EmbeddedUUID) o;
        return Objects.equals(uuid, that.uuid); // Compare uuid values
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid); // Generate hash based on uuid
    }
}
