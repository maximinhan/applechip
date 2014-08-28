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
    this.jdbcType = properties.getProperty("jdbc.type");
    this.setJdbcProperties(properties);
    this.setHibernateProperties(properties);
  }

  private void setHibernateProperties(Properties properties) {
    Properties bean = new Properties();
    bean.put(Environment.DIALECT, HibernateConstant.HIBERNATE_DIALECT_MAP.get(this.jdbcType));
    bean.put(Environment.SHOW_SQL, properties.getProperty("hibernate.show_sql"));
    bean.put(Environment.FORMAT_SQL, properties.getProperty("hibernate.format_sql"));
    bean.put(Environment.USE_SQL_COMMENTS, properties.getProperty("hibernate.use_sql_comments"));
    bean.put(Environment.QUERY_SUBSTITUTIONS, properties.getProperty("hibernate.query.substitutions"));
    bean.put(Environment.HBM2DDL_AUTO, properties.getProperty("hibernate.hbm2ddl.auto"));
    bean.put(Environment.USE_QUERY_CACHE, properties.getProperty("hibernate.cache.use_query_cache"));
    bean.put(Environment.USE_SECOND_LEVEL_CACHE, properties.getProperty("hibernate.cache.use_second_level_cache"));
    bean.put(Environment.CACHE_REGION_FACTORY, properties.getProperty("hibernate.cache.region.factory_class"));
    bean.put(Environment.CACHE_PROVIDER_CONFIG, properties.getProperty("hibernate.cache.provider_configuration_file_resource_path"));

    bean.put(Environment.DRIVER, HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    bean.put(Environment.URL, properties.getProperty("jdbc.url"));
    // bean.put(Environment.USER, properties.getProperty("jdbc.username"));
    // bean.put(Environment.PASS, properties.getProperty("jdbc.password"));
    this.hibernateProperties = bean;
  }

  private void setJdbcProperties(Properties properties) {
    properties.put("jdbc.driverClassName", HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    properties.put("jdbc.validationQuery", HibernateConstant.JDBC_VALIDATION_QUERY_MAP.get(this.jdbcType));
    Properties bean = new Properties();
    for (String string : HibernateConstant.JDBC_PROPERTIES_LIST) {
      String key = String.format("jdbc.%s", string);
      if (properties.containsKey(key)) {
        bean.put(string, properties.getProperty(key));
      }
    }
    this.dataSourceProperties = bean;
  }
}
