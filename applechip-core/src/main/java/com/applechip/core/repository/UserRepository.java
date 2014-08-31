package com.applechip.core.repository;

import com.applechip.core.abstact.GenericRepository;
import com.applechip.core.entity.User;

public interface UserRepository extends GenericRepository<User, String> {

  User getUserByUsername(String username);
}
