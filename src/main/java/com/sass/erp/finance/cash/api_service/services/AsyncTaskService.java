package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.entities.generics.system.AsyncTaskEntity;
import com.sass.erp.finance.cash.api_service.models.enums.TaskState;
import com.sass.erp.finance.cash.api_service.models.repositories.AsyncTaskRepository;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class AsyncTaskService extends CommonService<AsyncTaskEntity, EmbeddedIdentifier> {

  @Autowired
  protected AsyncTaskRepository asyncTaskRepository;

  @Override
  protected BaseRepository<AsyncTaskEntity, EmbeddedIdentifier> getRepository() {
    return asyncTaskRepository;
  }

  public AsyncTaskEntity createNewAsyncTask(String name){
    AsyncTaskEntity asyncTaskEntity = new AsyncTaskEntity();

    asyncTaskEntity.setTaskName(name);
    asyncTaskEntity.setTaskProgress(0.0);
    asyncTaskEntity.setTaskState(TaskState.PENDING);

    return this.asyncTaskRepository.save(
      this.prePersistAuditLog(
        this.prePersistIdentifier(asyncTaskEntity)
      )
    );
  }

  public Optional<AsyncTaskEntity> find(String id) {
    UUID uuid = UUID.fromString(id);
    EmbeddedIdentifier embeddedIdentifier = new EmbeddedIdentifier();
    embeddedIdentifier.setUuid(uuid);

    return Optional.ofNullable(this.asyncTaskRepository
      .findById(embeddedIdentifier)
      .orElseThrow(() -> new EntityNotFoundException("Task with id: " + id + "not found")));
  }

  public AsyncTaskEntity updateProgress(AsyncTaskEntity entity, double progress) {
    entity.setTaskProgress(progress);
    return this.asyncTaskRepository.save(
      this.preUpdateAuditLog(entity)
    );
  }

  public AsyncTaskEntity updateState(AsyncTaskEntity entity, TaskState taskState) {
    entity.setTaskState(taskState);
    return this.asyncTaskRepository.save(
      this.preUpdateAuditLog(entity)
    );
  }
}
