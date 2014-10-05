package com.applechip.web;

import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public abstract class AbstractTransactionTest extends AbstractTest {
  // @PersistenceContext(unitName = BaseConstant.PERSISTENCE_UNIT_NAME)
  // protected EntityManager entityManager;
}
