package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.List;

public class GenericServiceImpl<T extends GenericEntity<PK>, PK extends Serializable> implements GenericService<T, PK> {

  protected GenericRepository<T, PK> repository;

  public GenericServiceImpl(GenericRepository<T, PK> repository) {
    this.repository = repository;
  }

  @Override
  public List<T> getAll() {
    return repository.getAll();
  }

  @Override
  public List<T> getAllDistinct() {
    return repository.getAllDistinct();
  }

  @Override
  public long getCount() {
    return repository.getCount();
  }

  @Override
  public T get(PK id) {
    return repository.get(id);
  }

  @Override
  public boolean exist(PK id) {
    return repository.exist(id);
  }

  @Override
  public T merge(T object) {
    return repository.merge(object);
  }

  @Override
  public void remove(T object) {
    repository.remove(object);
  }

  @Override
  public void remove(PK id) {
    repository.remove(id);
  }
}
