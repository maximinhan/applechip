package com.applechip.core.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.applechip.core.entity.User;
import com.applechip.core.util.FieldUtil;
import com.applechip.core.util.SecurityUtil;

@Getter
@Slf4j
public class DatabaseProperties {
	public static Set<String> DATA_SOURCE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
		private static final long serialVersionUID = 7526702150204237983L;
		{
			addAll(FieldUtil.readFieldList(BasicDataSourceFactory.class, String.class));
		}
	});

	public static Set<String> HIBERNATE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
		private static final long serialVersionUID = 533898394580130849L;
		{
			addAll(FieldUtil.readFieldList(org.hibernate.cfg.AvailableSettings.class, String.class));
			addAll(FieldUtil.readFieldList(org.hibernate.jpa.AvailableSettings.class, String.class));
			addAll(FieldUtil.readFieldList(org.hibernate.envers.configuration.EnversSettings.class, String.class));
		}
	});

	private Properties hibernateProperties;

	private Properties dataSourceProperties;

	private Properties transactionProperties;

	private String[] packagesToScan;

	public static DatabaseProperties getInstance(Properties properties) {
		return new DatabaseProperties(properties);
	}

	private DatabaseProperties(Properties properties) {
		this.setPackagesToScan();
		this.setTransactionProperties();
		this.setDataSourceProperties(properties);
		this.setHibernateProperties(properties);
	}

	private void setPackagesToScan() {
		List<String> list = new ArrayList<String>();
		list.add(User.class.getPackage().getName());
		this.packagesToScan = list.toArray(new String[list.size()]);
	}

	private void setTransactionProperties() {
		Properties properties = new Properties();
		properties.setProperty("exists*", "PROPAGATION_REQUIRED,readOnly");// ISOLATION_READ_UNCOMMITTED,timeout_30,-Exception
		properties.setProperty("count*", "PROPAGATION_REQUIRED,readOnly");
		properties.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
		properties.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
		properties.setProperty("*", "PROPAGATION_REQUIRED");
		this.transactionProperties = properties;
	}

	private void setDataSourceProperties(Properties properties) {
		Properties result = new Properties();
		// this.jdbcType = properties.getProperty("type");
		for (String string : DATA_SOURCE_PROPERTIES_SET) {
			if (properties.containsKey(string)) {
				String value = properties.getProperty(string);
				result.put(string, SecurityUtil.forceDecrypt(value));
				log.info("setDataSourceProperties put finish... key: {}, value: {}", string, value);
			}
		}
		this.dataSourceProperties = result;
	}

	private void setHibernateProperties(Properties properties) {
		Properties result = new Properties();
		for (String string : HIBERNATE_PROPERTIES_SET) {
			if (properties.containsKey(string)) {
				String value = properties.getProperty(string);
				result.put(string, SecurityUtil.forceDecrypt(value));
				log.info("setHibernateProperties put finish... key: {}, value: {}", string, value);
			}
		}
		this.hibernateProperties = result;
	}
}
