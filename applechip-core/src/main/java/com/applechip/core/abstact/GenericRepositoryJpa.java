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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.applechip.core.constant.CoreConstant;

public class GenericRepositoryJpa<T extends GenericEntity<PK>, PK extends Serializable> implements GenericRepository<T, PK> {

  protected final Log log = LogFactory.getLog(getClass());

  @PersistenceContext(unitName = CoreConstant.PERSISTENCE_UNIT_NAME)
  public EntityManager entityManager;

  private Class<T> persistentClass;

  public GenericRepositoryJpa(final Class<T> persistentClass) {
    this.persistentClass = persistentClass;
  }

  public GenericRepositoryJpa(final Class<T> persistentClass, EntityManager entityManager) {
    this.persistentClass = persistentClass;
    this.entityManager = entityManager;
  }

  @Override
  public EntityManager getEntityManager() {
    return this.entityManager;
  }

  public CriteriaBuilder getCriteriaBuilder() {
    return this.entityManager.getCriteriaBuilder();
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
    } catch (NoResultException exception) {
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
  public void persist(T object) {
    this.entityManager.persist(object);
  }

  @Override
  public void remove(T object) {
    this.entityManager.remove(object);
  }

  @Override
  public void remove(PK id) {
    this.entityManager.remove(get(id));
  }

  // @Override
  // public <S extends T> S save(S entity) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public <S extends T> Iterable<S> save(Iterable<S> entities) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public T findOne(PK id) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public boolean exists(PK id) {
  // // TODO Auto-generated method stub
  // return false;
  // }
  //
  // @Override
  // public Iterable<T> findAll() {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public Iterable<T> findAll(Iterable<PK> ids) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public long count() {
  // // TODO Auto-generated method stub
  // return 0;
  // }
  //
  // @Override
  // public void delete(PK id) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void delete(T entity) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void delete(Iterable<? extends T> entities) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void deleteAll() {
  // // TODO Auto-generated method stub
  //
  // }
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  //
  // @Override
  // public Page<T> findAll(Pageable pageable) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public <S extends T> S save(S entity) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public T findOne(PK id) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public boolean exists(PK id) {
  // // TODO Auto-generated method stub
  // return false;
  // }
  //
  // @Override
  // public long count() {
  // // TODO Auto-generated method stub
  // return 0;
  // }
  //
  // @Override
  // public void delete(PK id) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void delete(T entity) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void delete(Iterable<? extends T> entities) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void deleteAll() {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public List<T> findAll() {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public List<T> findAll(Sort sort) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public List<T> findAll(Iterable<PK> ids) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public <S extends T> List<S> save(Iterable<S> entities) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public void flush() {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public T saveAndFlush(T entity) {
  // // TODO Auto-generated method stub
  // return null;
  // }
  //
  // @Override
  // public void deleteInBatch(Iterable<T> entities) {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public void deleteAllInBatch() {
  // // TODO Auto-generated method stub
  //
  // }
  //
  // @Override
  // public T getOne(PK id) {
  // // TODO Auto-generated method stub
  // return null;
  // }

}