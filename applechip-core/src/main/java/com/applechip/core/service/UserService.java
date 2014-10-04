package com.applechip.core.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.entity.member.User;

public interface UserService extends GenericService<User, String>, UserDetailsService {

	User loadUserByUsername(String username) throws UsernameNotFoundException;

}
