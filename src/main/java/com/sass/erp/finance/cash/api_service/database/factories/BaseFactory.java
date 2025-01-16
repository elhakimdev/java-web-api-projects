package com.sass.erp.finance.cash.api_service.database.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedAuditLog;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedTimeStamp;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedUUID;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFactory<T> {

    protected Logger logger = LoggerFactory.getLogger(BaseFactory.class);

    protected final Faker faker = new Faker();

    @Getter
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    @Setter
    public T entity;

    @Getter
    protected EmbeddedUUID id = new EmbeddedUUID();

    @Getter
    protected EmbeddedTimeStamp timeStamp = new EmbeddedTimeStamp();

    @Getter
    protected EmbeddedAuditLog auditLog = new EmbeddedAuditLog();

    public abstract void definition() throws JsonProcessingException;

    public BaseFactory(){
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void create(Integer count) throws JsonProcessingException {
        try {
            for (int i = 0; i < count; i++) {
                this.definition();
            }
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
