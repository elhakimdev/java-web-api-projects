package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Embeddable
public class EmbeddedTimeStamp {
    @Getter
    @Setter
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Getter
    @Setter
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Getter
    @Setter
    @Column(name = "deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @Getter
    @Setter
    @Column(name = "restored_at", nullable = true)
    private LocalDateTime restoredAt;
}
