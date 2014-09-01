package com.applechip.core;

import lombok.extern.slf4j.Slf4j;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {ApplicationContextTest.class})
@TransactionConfiguration(defaultRollback = true)
@Slf4j
public abstract class AbstractTest {
  public void before() throws Exception {
    log.debug("getTestClassName():" + getClass().getSimpleName());
  }
}
