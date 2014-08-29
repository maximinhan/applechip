package com.applechip.core;

import org.springframework.test.context.transaction.TransactionConfiguration;

@TransactionConfiguration(transactionManager = "annotationDrivenTransactionManager", defaultRollback = false)
public abstract class AbstractTransactionTest extends AbstractTest {

}
