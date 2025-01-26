package com.sass.erp.finance.cash.api_service.models.entities.generics.system;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.enums.TaskState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_GENERIC_SYSTEM_ASYNC_TASK")
public class AsyncTaskEntity extends BaseEntity {
  @Column(name = "name")
  private String taskName;

  @Column(name = "progress")
  private Double taskProgress;

  @Column(name = "state")
  private TaskState taskState;
}
