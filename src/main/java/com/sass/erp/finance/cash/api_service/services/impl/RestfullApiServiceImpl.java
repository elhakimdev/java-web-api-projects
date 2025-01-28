package com.sass.erp.finance.cash.api_service.services.impl;

import com.sass.erp.finance.cash.api_service.models.entities.BaseEntity;
import com.sass.erp.finance.cash.api_service.models.entities.embedable.EmbeddedIdentifier;
import com.sass.erp.finance.cash.api_service.models.repositories.BaseRepository;
import com.sass.erp.finance.cash.api_service.services.RestfullApiService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RestfullApiServiceImpl<T extends BaseEntity, ID extends EmbeddedIdentifier> implements RestfullApiService<T, ID> {

  /**
   * The repository instance.
   */
  protected BaseRepository<T, ID> repository;

  /**
   * Fetch all entities.
   *
   * @return All entities.
   */
  @Override
  public List<T> findAll() {
    return repository.findAll();
  }

  /**
   * Find entity by given UUID
   *
   * @param id The embedded UUID associated for this entity
   * @return The entity.
   * @throws EntityNotFoundException Exception thrown where entity not found.
   */
  @Override
  public T findByUUID(ID id) throws EntityNotFoundException {
    return Optional
      .ofNullable(repository.findByIdentifier(id))
      .orElseThrow(() -> new EntityNotFoundException("Entity with id: ["+ id.getUuid().toString() +" ] not found"));
  }

  /**
   * Sve new entity.
   *
   * @param entity Entity being saved.
   * @return New entity.
   */
  @Override
  public T save(T entity) {
    return repository.save(entity);
  }

  /**
   * Update entity by given identifier with the new entity.
   *
   * @param id     The embedded UUID associated for this entity.
   * @param entity The entity.
   * @return Updated entity.
   * @throws EntityNotFoundException Exception thrown where entity not found.
   */
  @Override
  public T update(ID id, T entity) throws EntityNotFoundException {

    T byIdentifier = repository.findByIdentifier(id);

    patchEntity(byIdentifier, entity);

    return repository.save(byIdentifier);
  }

  /**
   * Delete entity by given UUID.
   *
   * @param id UUID to find being deleted entity
   * @throws EntityNotFoundException Exception thrown where entity not found.
   */
  @Override
  public void delete(ID id) throws EntityNotFoundException {

    T byIdentifier = repository.findByIdentifier(id);

    repository.delete(byIdentifier);
  }

  /**
   * Patch new entity with new attributes.
   * @param source Source Entity.
   * @param target Target Entity.
   */
  private void patchEntity(T source, T target){
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  /**
   * Get names of null properties in the given object.
   */
  private String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    return Arrays.stream(src.getPropertyDescriptors())
      .map(FeatureDescriptor::getName)
      .filter(propertyName -> src.getPropertyValue(propertyName) == null)
      .toArray(String[]::new);
  }
}
