package com.applechip.core.properties;

import java.util.Properties;

import lombok.Getter;

import org.hibernate.cfg.Environment;

import com.applechip.core.constant.HibernateConstant;

@Getter
public class HibernateProperties {
  private String jdbcType;
  private Properties hibernateProperties;
  private Properties dataSourceProperties;

  public static HibernateProperties getProperties(Properties properties) {
    return new HibernateProperties(properties);
  }

  private HibernateProperties(Properties properties) {
    this.jdbcType = properties.getProperty("type");
    this.setJdbcProperties(properties);
    this.setHibernateProperties(properties);
  }

  private void setHibernateProperties(Properties properties) {
    properties.put(Environment.DRIVER, HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    properties.put(Environment.DIALECT, HibernateConstant.HIBERNATE_DIALECT_MAP.get(this.jdbcType));
    Properties bean = new Properties();
    for (String string : HibernateConstant.HIBERNATE_PROPERTIES_SET) {
      if (properties.containsKey(string)) {
        bean.put(string, properties.getProperty(string));
      }
    }
//    bean.put(Environment.URL, properties.getProperty("url"));
    // bean.put(Environment.USER, properties.getProperty("jdbc.username"));
    // bean.put(Environment.PASS, properties.getProperty("jdbc.password"));
    this.hibernateProperties = bean;
  }

  private void setJdbcProperties(Properties properties) {
    properties.put("driverClassName", HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    properties.put("validationQuery", HibernateConstant.JDBC_VALIDATION_QUERY_MAP.get(this.jdbcType));
    Properties bean = new Properties();
    for (String string : HibernateConstant.DATA_SOURCE_PROPERTIES_SET) {
      if (properties.containsKey(string)) {
        bean.put(string, properties.getProperty(string));
      }
    }
    this.dataSourceProperties = bean;
  }
}
