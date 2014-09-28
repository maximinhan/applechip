package com.applechip.core.util;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.applechip.core.properties.DatabaseProperties;

@Slf4j
public class FieldUtilTest {

	@Test
	public void testReadFieldList() throws Exception {
		for (String str : DatabaseProperties.DATA_SOURCE_PROPERTIES_SET) {
			log.debug("DatabaseProperties.DATA_SOURCE_PROPERTIES_SET... source: {}", str);
		}
		for (String str : DatabaseProperties.HIBERNATE_PROPERTIES_SET) {
			log.debug("DatabaseProperties.HIBERNATE_PROPERTIES_SET... source: {}", str);
		}
	}
}
