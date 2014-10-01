package com.applechip.core;

import lombok.extern.slf4j.Slf4j;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.applechip.core.util.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { ApplicationContextTest.class })
@TransactionConfiguration(defaultRollback = true)
@Slf4j
public abstract class AbstractTest {

	@Rule
	public TestName testName = new TestName();

	@Rule
	public TestRule watchman = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			String methodName = StringUtil.defaultIfBlank(description.getMethodName(), "before... ");
			String className = StringUtil.substringAfterLast(description.getClassName(), ".");
			log.debug("starting... test className: {}, methodName: {}", className, methodName);
		}
	};

}
