package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@ToString
@Embeddable
public class EmbeddedUUID  implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}
