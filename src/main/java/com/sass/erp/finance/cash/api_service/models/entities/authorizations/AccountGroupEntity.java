package com.sass.erp.finance.cash.api_service.models.entities.authorizations;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CASH_AUTHORIZATION_ACCOUNT_GROUPS")
public class AccountGroupEntity extends BaseEntity {
  @Column(name = "account_group_name")
  private String accountGroupName;

  @Column(name = "account_group_description")
  private String accountGroupDescription;
}
