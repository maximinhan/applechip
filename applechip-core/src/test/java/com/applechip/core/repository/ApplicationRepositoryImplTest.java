package com.applechip.core.repository;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.applechip.core.AbstractTransactionTest;

@Slf4j
public class ApplicationRepositoryImplTest extends AbstractTransactionTest {

  @Autowired
  private ApplicationRepository applicationRepository;

  @Test
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
  public void testGetCodes() {
    log.debug("{}", applicationRepository.getCategories());
  }

}
