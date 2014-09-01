package com.applechip.core.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.abstact.GenericService;
import com.applechip.core.entity.User;


public interface UserService extends GenericService<User, String>, UserDetailsService {

  public User loadUserByUsername(String username) throws UsernameNotFoundException;

}
