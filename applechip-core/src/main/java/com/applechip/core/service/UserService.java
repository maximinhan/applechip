package com.applechip.core.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.applechip.core.abstact.GenericService;
import com.applechip.core.entity.User;


public interface UserService extends GenericService<User, String>, UserDetailsService {

}
