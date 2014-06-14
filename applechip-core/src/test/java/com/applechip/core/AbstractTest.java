package com.applechip.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { ApplicationContextTest.class })
public abstract class AbstractTest {

  protected final Log log = LogFactory.getLog(getClass());

  public void before() throws Exception {
    log.debug("getTestClassName():" + getClass().getSimpleName());
  }
}
