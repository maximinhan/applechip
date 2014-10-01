package com.applechip.core.repository;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.applechip.core.AbstractTransactionTest;
import com.applechip.core.entity.User;
import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.properties.RuntimeProperties;

@Slf4j
public class UserRepositoryImplTest extends AbstractTransactionTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private RuntimeProperties runtimeProperties;

	@Test
//	@Ignore
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_UNCOMMITTED)
	public void testGetAll() throws Exception {
		log.debug("{}", applicationProperties);
		log.debug("{}", runtimeProperties);
		log.debug("{}", userRepository.findAll());
	}

	// @Test
	// public void testGetAllss() throws Exception {
	// log.debug("{}", userRepository.findUsernameAll());
	// }

	@Test
	@Ignore
	public void testMerge() throws Exception {
		User user = new User();
		user.setId("vicki");
		user.setUsername("123");
		user.setPassword("123");
		log.debug("{}", userRepository.save(user));
	}
}
