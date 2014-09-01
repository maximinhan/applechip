package com.applechip.core.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.AbstractTest;
import com.applechip.core.entity.User;


public class UserServiceImplTest extends AbstractTest {

  @Autowired
  private UserService userService;

  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByUsername() throws Exception {
    log.debug(userService.loadUserByUsername("username"));
  }

  @Test
  public void testMerge1() throws Exception {
    User user = null;
    try {
      user = userService.loadUserByUsername("username");
    } catch (UsernameNotFoundException e) {
      user = getUser();
    }
    user.setPassword("testMerge1");
    userService.merge(user);
  }

  @Test
  public void testMerge2() throws Exception {
    User user = null;
    try {
      user = userService.loadUserByUsername("username");
    } catch (UsernameNotFoundException e) {
      user = getUser();
    }
    user.setPassword("testMerge2");
    userService.merge(user);
  }

  @Test
  public void testGet() throws Exception {
    log.debug(userService.getAll());
  }

  private User getUser() {
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    return user;
  }
}
