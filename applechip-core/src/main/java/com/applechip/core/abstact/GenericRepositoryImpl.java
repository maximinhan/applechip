package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.applechip.core.constant.CoreConstant;

public class GenericRepositoryImpl<T extends GenericEntity<PK>, PK extends Serializable> implements GenericRepository<T, PK> {

  @PersistenceContext(unitName = CoreConstant.PERSISTENCE_UNIT_NAME)
  protected EntityManager entityManager;

  private Class<T> persistentClass;

  public GenericRepositoryImpl(final Class<T> persistentClass) {
    this.persistentClass = persistentClass;
  }

  @Override
  public List<T> getAll() {
    CriteriaQuery<T> query = getCriteriaBuilder().createQuery(this.persistentClass);
    query.select(query.from(this.persistentClass));
    return this.entityManager.createQuery(query).getResultList();
  }

  @Override
  public List<T> getAllDistinct() {
    Collection<T> result = new LinkedHashSet<T>(getAll());
    return new ArrayList<T>(result);
  }

  @Override
  public long getCount() {
    CriteriaBuilder builder = getCriteriaBuilder();
    CriteriaQuery<Long> query = builder.createQuery(Long.class);
    query.select(builder.count(query.from(this.persistentClass)));
    try {
      return this.entityManager.createQuery(query).getSingleResult();
    } catch (NoResultException e) {
      return 0;
    }
  }

  @Override
  public T get(PK id) {
    return this.entityManager.find(this.persistentClass, id);
  }

  @Override
  public boolean exist(PK id) {
    return get(id) != null;
  }

  @Override
  public T merge(T object) {
    return this.entityManager.merge(object);
  }

  @Override
  public void remove(T object) {
    this.entityManager.remove(object);
  }

  @Override
  public void remove(PK id) {
    this.entityManager.remove(get(id));
  }

  private CriteriaBuilder getCriteriaBuilder() {
    return this.entityManager.getCriteriaBuilder();
  }
}
