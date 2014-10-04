package com.applechip.core.repository;

import com.applechip.core.entity.member.User;

public interface UserRepository extends GenericRepository<User, String> {
  User getUserByUsername(String username);
}
