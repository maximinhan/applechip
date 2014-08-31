package com.applechip.core.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.AbstractTransactionTest;
import com.applechip.core.entity.User;


public class UserServiceImplTest extends AbstractTransactionTest {

  @Autowired
  private UserService userService;

  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByUsername() throws Exception {
    log.debug(userService.loadUserByUsername("username"));
  }

  @Test
  public void testMerge() throws Exception {
    User user = new User();
    user.setId("username");
    user.setUsername("merge");
    user.setPassword("merge");
    log.debug(userService.merge(user));
  }
}
