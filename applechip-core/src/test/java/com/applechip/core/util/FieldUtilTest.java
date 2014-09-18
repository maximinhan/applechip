package com.applechip.core.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import com.applechip.core.constant.DatabaseConstant;

public class FieldUtilTest {

	@Test
	public void testReadFieldList() throws Exception {
		for (String str : FieldUtil.readFieldList(BasicDataSourceFactory.class, String.class)) {
			System.out.println(str);
		}
		System.out.println("BasicDataSourceFactory.class\nend\n");
		for (String str : FieldUtil.readFieldList(org.hibernate.cfg.AvailableSettings.class, String.class)) {
			System.out.println(str);
		}
		System.out.println("org.hibernate.cfg.AvailableSettings.class\nend\n");
		for (String str : FieldUtil.readFieldList(org.hibernate.jpa.AvailableSettings.class, String.class)) {
			System.out.println(str);
		}
		System.out.println("org.hibernate.jpa.AvailableSettings.class\nend\n");
	}

	@Test
	public void testReadField() {
		for (String str : DatabaseConstant.HIBERNATE_PROPERTIES_SET) {
			System.out.println(str);
		}
	}

}
