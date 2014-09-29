package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.util.IteratorUtil;

//@RepositoryDefinition(domainClass = Employee.class, idClass = Long.class)
public class GenericRepositoryImpl<T extends GenericEntity<PK>, PK extends Serializable> implements
		GenericRepository<T, PK> {

	@PersistenceContext(unitName = ApplicationConstant.PERSISTENCE_UNIT_NAME)
	protected EntityManager entityManager;

	private Class<T> clazz;

	public GenericRepositoryImpl(final Class<T> persistentClass) {
		this.clazz = persistentClass;
	}

	@Override
	public Iterable<T> findAll(Sort sort) {
		CriteriaQuery<T> criteriaQuery = this.getCriteriaBuilder().createQuery(this.clazz);
		criteriaQuery.select(criteriaQuery.from(this.clazz));
		criteriaQuery.orderBy(IteratorUtil.toList(sort.iterator()));// IteratorUtil.newArrayList(sort.iterator());
		return this.entityManager.createQuery(criteriaQuery).getResultList();
	}

	// java8...
	// public static <T> ArrayList<T> toArrayList(final Iterator<T> iterator) {
	// return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator,
	// Spliterator.ORDERED), false)
	// .collect(Collectors.toCollection(ArrayList::new));
	// }

	@Override
	public Page<T> findAll(Pageable pageable) {
		CriteriaQuery<T> criteriaQuery = this.getCriteriaBuilder().createQuery(this.clazz);
		criteriaQuery.select(criteriaQuery.from(this.clazz));
		criteriaQuery.orderBy(IteratorUtil.toList(pageable.getSort().iterator()));
		TypedQuery<T> typedQuery = this.entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageable.getOffset());
		typedQuery.setMaxResults((pageable.getOffset() * pageable.getPageNumber()) + pageable.getPageSize());
		List<T> list = typedQuery.getResultList();
		return new PageImpl<T>(list, pageable, list.size());
	}

	@Override
	public <S extends T> S save(S entity) {
		return this.entityManager.merge(entity);
	}

	@Override
	public <S extends T> Iterable<S> save(Iterable<S> entities) {
		Collection<S> collection = new LinkedHashSet<S>();
		for (S entity : entities) {
			collection.add(this.save(entity));
		}
		return new ArrayList<S>(collection);
	}

	@Override
	public T findOne(PK id) {
		return this.entityManager.find(this.clazz, id);
	}

	@Override
	public boolean exists(PK id) {
		return this.findOne(id) != null;
	}

	@Override
	public Iterable<T> findAll() {
		CriteriaQuery<T> criteriaQuery = this.getCriteriaBuilder().createQuery(this.clazz);
		criteriaQuery.select(criteriaQuery.from(this.clazz));
		return this.entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Iterable<T> findAll(Iterable<PK> ids) {
		CriteriaQuery<T> criteriaQuery = this.getCriteriaBuilder().createQuery(this.clazz);
		Root<T> root = criteriaQuery.from(this.clazz);
		criteriaQuery.where(getPredicates(root, ids, "id"));
		criteriaQuery.select(root);
		return this.entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public long count() {
		CriteriaBuilder criteriaBuilder = this.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(this.clazz)));
		try {
			return this.entityManager.createQuery(criteriaQuery).getSingleResult();
		}
		catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public void delete(PK id) {
		this.entityManager.remove(this.findOne(id));
	}

	@Override
	public void delete(T entity) {
		this.entityManager.remove(entity);

	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		for (T entity : entities) {
			this.delete(entity);
		}
	}

	@Override
	public void deleteAll() {
		for (T entity : this.findAll()) {
			this.delete(entity);
		}
	}

	private CriteriaBuilder getCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}

	private Predicate[] getPredicates(Root<T> root, Iterable<PK> ids, String pk) {
		Predicate[] predicates = { root.get(pk).in(ids) };
		return predicates;
	}
}
