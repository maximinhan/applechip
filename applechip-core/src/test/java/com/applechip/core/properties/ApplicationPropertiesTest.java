package com.applechip.core.properties;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.applechip.core.AbstractTest;

@Slf4j
public class ApplicationPropertiesTest extends AbstractTest {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private RuntimeProperties runtimeProperties;

	@Autowired
	private DatabaseProperties databaseProperties;

	@Test
	public void testProperties() {
		log.debug("applicationProperties: {}", applicationProperties.toString());
		log.debug("runtimeProperties: {}", runtimeProperties.toString());
		log.debug("databaseProperties: {}", databaseProperties.toString());
	}

}
