package com.applechip.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.applechip.core.entity.member.User;
import com.applechip.core.repository.UserRepository;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService { // implements SocialUserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}

	@Override
//	@Cacheable(value = "loadUserByUsername", key = "#username")
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
//		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
//        return (SocialUserDetails) userDetails;
		return user;
	}
}
