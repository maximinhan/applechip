package com.applechip.core.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.AbstractTest;
import com.applechip.core.entity.User;

@Slf4j
public class UserServiceImplTest extends AbstractTest {

  @Autowired
  private UserService userService;

  @Test(expected = UsernameNotFoundException.class)
  @Ignore
  public void testLoadUserByUsername() throws Exception {
    log.debug("{}", userService.loadUserByUsername("username"));
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
    userService.save(user);
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
    userService.save(user);
  }

  @Test
  public void testGet() throws Exception {
    log.debug("{}", userService.findAll());
  }

  private User getUser() {
    User user = new User();
    user.setUsername("username");
    user.setPassword("password");
    return user;
  }
}
