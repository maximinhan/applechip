package com.applechip.core.abstact;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface GenericRepository<T extends GenericEntity<PK>, PK extends Serializable> {

	public Iterable<T> findAll(Sort sort);

	public Page<T> findAll(Pageable pageable);

	public <S extends T> S save(S entity);

	public <S extends T> Iterable<S> save(Iterable<S> entities);

	public T findOne(PK id);

	public boolean exists(PK id);

	public Iterable<T> findAll();

	public Iterable<T> findAll(Iterable<PK> ids);

	public long count();

	public void delete(PK id);

	public void delete(T entity);

	public void delete(Iterable<? extends T> entities);

	public void deleteAll();

}
