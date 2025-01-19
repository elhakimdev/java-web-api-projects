package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public abstract class CommonService<T extends BaseEntity, ID> {
    protected abstract BaseRepository<T, ID> getRepository();

    protected final CrudTaskService<T, ID> crudTaskService;

    public CommonService(CrudTaskService<T, ID> crudTaskService) {
        this.crudTaskService = crudTaskService;
    }
}
