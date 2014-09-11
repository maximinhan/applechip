package com.applechip.core.repository;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.applechip.core.AbstractTransactionTest;
import com.applechip.core.entity.User;


@Slf4j
public class UserRepositoryImplTest extends AbstractTransactionTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testGetAll() throws Exception {
    log.debug("{}", userRepository.findAll());
  }

  @Test
  public void testMerge() throws Exception {
    User user = new User();
    user.setId("vicki");
    user.setUsername("123");
    user.setPassword("123");
    log.debug("{}", userRepository.save(user));
  }
}
