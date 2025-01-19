package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Getter
@Service
public class CrudTaskService<T extends BaseEntity, ID> {

    protected BaseRepository<T, ID> repository;

    public CrudTaskService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T create(T entity){
        return this.getRepository().save(entity);
    }

    public List<T> read() {
        return this.getRepository().findAll();
    }
}
