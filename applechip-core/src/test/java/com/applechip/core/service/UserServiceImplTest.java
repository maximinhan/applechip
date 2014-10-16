package com.applechip.core.service;

import javax.persistence.NoResultException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.applechip.core.AbstractTest;

@Slf4j
public class UserServiceImplTest extends AbstractTest {

  @Autowired
  private UserService userService;

  @Autowired
  private BasicDataSource dataSource;

  @Test(expected = UsernameNotFoundException.class)
  public void testLoadUserByUsername() throws Exception {
    log.debug("{}", userService.loadUserByUsername("usernameZx"));
  }

  @Test(expected = NoResultException.class)
  public void testGet() throws Exception {
    log.debug("{}", userService.findOne("usernameZx"));
  }
}
