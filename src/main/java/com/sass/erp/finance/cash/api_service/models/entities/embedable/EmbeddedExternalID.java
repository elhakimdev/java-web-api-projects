package com.sass.erp.finance.cash.api_service.models.entities.embedable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@Embeddable
public class EmbeddedExternalID implements Serializable {
    private String externalId;
    private String uniqueId;
    private String systemRefId;
    private String displayId;
}
