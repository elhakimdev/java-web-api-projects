package com.sass.erp.finance.cash.api_service.models.repositories;

import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends EmbeddedIdentifier> extends JpaRepository<T, ID> {
  T findByIdentifier(EmbeddedIdentifier identifier) throws EntityNotFoundException;
}

