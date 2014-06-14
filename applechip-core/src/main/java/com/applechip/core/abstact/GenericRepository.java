package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public interface GenericRepository<T extends GenericEntity<PK>, PK extends Serializable> {

  public List<T> getAll();

  public List<T> getAllDistinct();

  public long getCount();

  public T get(PK id);

  public boolean exist(PK id);

  public T merge(T object);
  
  public void persist(T object);

  public void remove(T object);

  public void remove(PK id);

  EntityManager getEntityManager();

}
