package com.applechip.core.repository;

import org.springframework.stereotype.Repository;

import com.applechip.core.abstact.GenericRepositoryImpl;
import com.applechip.core.entity.User;

@Repository("userRepository")
public class UserRepositoryImpl extends GenericRepositoryImpl<User, String> implements UserRepository {

  public UserRepositoryImpl() {
    super(User.class);
  }
}
