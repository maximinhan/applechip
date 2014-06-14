package com.applechip.core;

import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class AbstractTransactionTest extends AbstractTest {

}
