package com.applechip.core.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.applechip.core.constant.DatabaseConstant;
import com.applechip.core.entity.User;
import com.applechip.core.util.CryptoUtil;

@Getter
@Slf4j
public class DatabaseProperties {
	private Properties hibernateProperties;

	private Properties dataSourceProperties;

	private Properties transactionProperties;

	private String[] packagesToScan;

	public static DatabaseProperties getInstance(Properties properties) {
		return new DatabaseProperties(properties);
	}

	private DatabaseProperties(Properties properties) {
		// this.jdbcType = properties.getProperty("type");
		// properties.put(Environment.DRIVER,
		// HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
		// properties.put(Environment.DIALECT,
		// HibernateConstant.HIBERNATE_DIALECT_MAP.get(this.jdbcType));
		// properties.put("driverClassName",
		// HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
		// properties.put("validationQuery",
		// HibernateConstant.JDBC_VALIDATION_QUERY_MAP.get(this.jdbcType));
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
		for (String string : DatabaseConstant.DATA_SOURCE_PROPERTIES_SET) {
			if (properties.containsKey(string)) {
				String value = properties.getProperty(string);
				result.put(string, CryptoUtil.forceDecrypt(value));
				log.info("setDataSourceProperties put finish... key: {}, value: {}", string, value);
			}
		}
		this.dataSourceProperties = result;
	}

	private void setHibernateProperties(Properties properties) {
		Properties result = new Properties();
		for (String string : DatabaseConstant.HIBERNATE_PROPERTIES_SET) {
			if (properties.containsKey(string)) {
				String value = properties.getProperty(string);
				result.put(string, value);
				log.info("setHibernateProperties put finish... key: {}, value: {}", string, value);
			}
		}
		this.hibernateProperties = result;
	}
}
