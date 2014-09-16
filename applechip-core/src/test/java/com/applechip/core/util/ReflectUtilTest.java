package com.applechip.core.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;


public class ReflectUtilTest {

	@Test
	public void testReadFieldList() throws Exception {
		for(String str : ReflectUtil.readFieldList(BasicDataSourceFactory.class, String.class)){
			System.out.println(str);
		}
		System.out.println("BasicDataSourceFactory.class\nend\n");
		for(String str : ReflectUtil.readFieldList(org.hibernate.cfg.AvailableSettings.class, String.class)){
			System.out.println(str);
		}
		System.out.println("org.hibernate.cfg.AvailableSettings.class\nend\n");
		for(String str : ReflectUtil.readFieldList(org.hibernate.jpa.AvailableSettings.class, String.class)){
			System.out.println(str);
		}
		System.out.println("org.hibernate.jpa.AvailableSettings.class\nend\n");
	}

}
