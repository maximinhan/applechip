package com.applechip.core.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.applechip.core.AbstractTransactionTest;
import com.applechip.core.entity.User;


public class UserRepositoryImplTest extends AbstractTransactionTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testGetAll() throws Exception {
    log.debug(userRepository.getAll());
  }

  @Test
  public void testMerge() throws Exception {
    User user = new User();
    user.setId("vicki");
    user.setUsername("123");
    user.setPassword("123");
    log.debug(userRepository.merge(user));
  }
}
