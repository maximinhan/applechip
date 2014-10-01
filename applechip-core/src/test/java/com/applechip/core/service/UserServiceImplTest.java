package com.applechip.core.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.AbstractTest;
import com.applechip.core.entity.User;

@Slf4j
public class UserServiceImplTest extends AbstractTest {

	@Autowired
	private UserService userService;

	@Autowired
	private BasicDataSource dataSource;

	@Test(expected = UsernameNotFoundException.class)
	//	@Ignore
	public void testLoadUserByUsername() throws Exception {
		log.debug("{}", userService.loadUserByUsername("usernameZx"));
	}

	@Test
	public void testMerge1() throws Exception {
		User user = null;
		try {
			user = userService.loadUserByUsername("username");
		}
		catch (UsernameNotFoundException e) {
			user = getUser();
		}
		user.setPassword("testMerge1");
		userService.save(user);
	}

	@Test
	public void testMerge2() throws Exception {
		User user = null;
		try {
			user = userService.loadUserByUsername("username");
			log.debug("dataSource.getNumActive(): {}", dataSource.getNumActive());
			log.debug("dataSource.getNumIdle(): {}", dataSource.getNumIdle());
			log.debug("dataSource.getNumTestsPerEvictionRun(): {}", dataSource.getNumTestsPerEvictionRun());
		}
		catch (UsernameNotFoundException e) {
			user = getUser();
		}
		user.setPassword("testMerge2");
		userService.save(user);
	}

	@Test
	public void testGet() throws Exception {
		log.debug("{}", userService.findAll());
	}

	@Test
	public void testFindAll() {
		log.debug("{}", userService.findAll(getList()));
	}

	private User getUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		return user;
	}

	private List<String> getList() {
		List<String> list = new ArrayList<String>();
		list.add("123");
		list.add("1234");
		list.add("12345");
		list.add("12346");
		return list;
	}
}
