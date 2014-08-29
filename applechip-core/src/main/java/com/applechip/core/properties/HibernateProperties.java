package com.applechip.core.properties;

import java.util.Properties;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import com.applechip.core.constant.HibernateConstant;
import com.applechip.core.exception.SystemException;
import com.applechip.core.util.CryptoUtil;

@Getter
@Slf4j
public class HibernateProperties {
  private String jdbcType;
  private Properties hibernateProperties;
  private Properties dataSourceProperties;

  public static HibernateProperties getProperties(Properties properties) {
    return new HibernateProperties(properties);
  }

  private HibernateProperties(Properties properties) {
    this.jdbcType = properties.getProperty("type");
    // properties.put(Environment.DRIVER,
    // HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    // properties.put(Environment.DIALECT,
    // HibernateConstant.HIBERNATE_DIALECT_MAP.get(this.jdbcType));
    // properties.put("driverClassName",
    // HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    // properties.put("validationQuery",
    // HibernateConstant.JDBC_VALIDATION_QUERY_MAP.get(this.jdbcType));
    this.setDataSourceProperties(properties);
    this.setHibernateProperties(properties);
  }

  private void setHibernateProperties(Properties properties) {
    Properties bean = new Properties();
    for (String string : HibernateConstant.HIBERNATE_PROPERTIES_SET) {
      if (properties.containsKey(string)) {
        bean.put(string, properties.getProperty(string));
        log.info("setHibernateProperties put finish... key: {}, value: {}", string, properties.getProperty(string));
      }
    }
    this.hibernateProperties = bean;
  }

  private void setDataSourceProperties(Properties properties) {
    Properties bean = new Properties();
    for (String string : HibernateConstant.DATA_SOURCE_PROPERTIES_SET) {
      if (properties.containsKey(string)) {
        String decrypt = decrypt(properties.getProperty(string));
        bean.put(string, decrypt);
        log.info("setJdbcProperties put finish... key: {}, value: {}", string, decrypt);
      }
    }
    this.dataSourceProperties = bean;
  }

  private String decrypt(String str) {
    try {
      return CryptoUtil.decrypt(str);
    } catch (SystemException e) {
      return str;
    }
  }
}
