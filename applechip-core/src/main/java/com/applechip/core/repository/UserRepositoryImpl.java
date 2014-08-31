package com.applechip.core.repository;

import org.springframework.stereotype.Repository;

import com.applechip.core.abstact.GenericRepositoryImpl;
import com.applechip.core.entity.QUser;
import com.applechip.core.entity.User;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<User, String> implements
    UserRepository {

  public UserRepositoryImpl() {
    super(User.class);
  }

  @Override
  public User getUserByUsername(String username) {
    JPAQuery query = new JPAQuery(entityManager);
    QUser user = QUser.user;
    query.from(user).where(user.username.eq(username));
    return query.singleResult(user);
  }
}
