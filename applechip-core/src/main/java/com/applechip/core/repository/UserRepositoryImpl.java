package com.applechip.core.repository;

import org.springframework.stereotype.Repository;

import com.applechip.core.entity.member.QUser;
import com.applechip.core.entity.member.User;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User, String> implements UserRepository {

  public UserRepositoryImpl() {
    super(User.class);
  }

  @Override
  public User getUserByUsername(String username) {
    entityManager.createQuery("from User where username = :username").setParameter("username", username).getSingleResult();
    JPAQuery query = new JPAQuery(entityManager);
    QUser user = QUser.user;
    query.from(user).where(user.username.eq(username));
    return query.singleResult(user);
  }

  // final List<Predicate> predicates = new ArrayList<Predicate>();
  // for (final Entry<String, String> e : myPredicateMap.entrySet()) {
  // final String key = e.getKey();
  // final String value = e.getValue();
  // if ((key != null) && (value != null)) {
  // if (value.contains("%")) {
  // predicates.add(criteriaBuilder.like(root.<String> get(key), value));
  // } else {
  // predicates.add(criteriaBuilder.equal(root.get(key), value));
  // }
  // }
  // }
  //
  // query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
  // query.select(count);

  // entityManager.createQuery("from User where username = :username").setParameter("username",
  // username).getSingleResult();

  // CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
  // CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
  // Root<User> root = criteriaQuery.from(User.class);
  // criteriaQuery.select(root);
  // criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
  // User user = null;
  // try {
  // user = this.entityManager.createQuery(criteriaQuery).getSingleResult();
  // } catch (NoResultException exception) {
  // throw new UsernameNotFoundException("username not found..." + username);
  // }
  // return user;
}
