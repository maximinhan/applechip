package com.applechip.core.repository;

import org.springframework.data.repository.RepositoryDefinition;

import com.applechip.core.abstact.GenericRepository;
import com.applechip.core.entity.User;

//@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository extends GenericRepository<User, String> {

	User getUserByUsername(String username);
}
