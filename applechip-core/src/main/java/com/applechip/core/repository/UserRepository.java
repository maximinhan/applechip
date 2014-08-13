package com.applechip.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<User> extends PagingAndSortingRepository<User, String> {
  User findByUserId(Long userId);
}
