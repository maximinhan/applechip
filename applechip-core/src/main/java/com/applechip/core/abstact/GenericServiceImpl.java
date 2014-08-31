package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenericServiceImpl<T extends GenericEntity<PK>, PK extends Serializable> implements GenericService<T, PK> {

  protected Log log = LogFactory.getLog(getClass());

  protected GenericRepository<T, PK> repository;

  public GenericServiceImpl() {
  }

  public GenericServiceImpl(GenericRepository<T, PK> repository) {
    this.repository = repository;
  }

  public List<T> getAll() {
    return repository.getAll();
  }

  public long getCount() {
    return repository.getCount();
  }

  public T get(PK id) {
    return repository.get(id);
  }

  public boolean exist(PK id) {
    return repository.exist(id);
  }

  public T merge(T object) {
    return repository.merge(object);
  }

  public void remove(T object) {
    repository.remove(object);
  }

  public void remove(PK id) {
    repository.remove(id);
  }
}
