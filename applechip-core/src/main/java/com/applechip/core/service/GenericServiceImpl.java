package com.applechip.core.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.applechip.core.entity.GenericEntity;
import com.applechip.core.repository.GenericRepository;

public class GenericServiceImpl<T extends GenericEntity<PK>, PK extends Serializable> implements GenericService<T, PK> {

	protected GenericRepository<T, PK> repository;

	public GenericServiceImpl(GenericRepository<T, PK> repository) {
		this.repository = repository;
	}

	@Override
	public Iterable<T> findAll(Sort sort) {
		return repository.findAll();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public <S extends T> S save(S entity) {
		return repository.save(entity);
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		return repository.save(entities);
	}

	@Override
	public T findOne(PK id) {
		return repository.findOne(id);
	}

	@Override
	public boolean exists(PK id) {
		return repository.exists(id);
	}

	@Override
	public Iterable<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Iterable<T> findAll(Iterable<PK> ids) {
		return repository.findAll(ids);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(PK id) {
		repository.delete(id);
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		repository.delete(entities);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
}
