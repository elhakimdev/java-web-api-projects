package com.sass.erp.finance.cash.api_service.services;

import com.sass.erp.finance.cash.api_service.http.requests.concerns.AdvanceSearchRequest;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestfullApiService<T, ID extends EmbeddedIdentifier> {

  Page<T> search(AdvanceSearchRequest request, Pageable pageable);
  /**
   * Fetch all entities.
   * @return All entities.
   */
  List<T> findAll();  /**
   * Fetch all entities.
   * @return All entities.
   */
  Page<T> findAll(PageRequest pageRequest);

  /**
   * Find entity by given UUID
   * @param id The embedded UUID associated for this entity
   * @return The entity.
   * @throws EntityNotFoundException Exception thrown where entity not found.
   */
  T findByUUID(ID id) throws EntityNotFoundException;

  T findByIdentifier(EmbeddedIdentifier id) throws EntityNotFoundException;

  /**
   * Sve new entity.
   *
   * @param entity Entity being saved.
   * @return New entity.
   */
  T save(T entity);

  /**
   * Update entity by given identifier with the new entity.
   *
   * @param id The embedded UUID associated for this entity.
   * @param entity The entity.
   * @return Updated entity.
   * @throws EntityNotFoundException Exception thrown where entity not found.
   */
  T update(ID id, T entity) throws EntityNotFoundException;

  /**
   * Delete entity by given UUID.
   *
   * @param id UUID to find being deleted entity
   * @throws EntityNotFoundException Exception thrown where entity not found.
   */
  void delete(ID id) throws EntityNotFoundException;
}
