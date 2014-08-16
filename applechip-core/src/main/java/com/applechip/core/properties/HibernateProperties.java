package com.applechip.core.properties;

import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.cfg.Environment;

import com.applechip.core.constant.CoreConstant;
import com.applechip.core.constant.HibernateConstant;

@Getter
@Setter
public class HibernateProperties {
  private String jdbcType;
  private String jdbcDriverClassName;
  private String jdbcUrl;
  private String jdbcUsername;
  private String jdbcPassword;
  private String jdbcValidationQuery;
  private int jdbcMaxActive;
  private int jdbcMaxWait;
  private int jdbcMinIdle;
  private int jdbcInitialSize;
  private boolean jdbcTestOnBorrow;
  private boolean jdbcPoolingStatements;
  private boolean jdbcDefaultAutoCommit;
  private boolean jdbcRemoveAbandoned;
  private int jdbcRemoveAbandonedTimeout;
  private boolean jdbcTestOnReturn;
  private boolean jdbcTestWhileIdle;
  private int jdbcTimeBetweenEvictionRunsMillis;
  private int jdbcNumTestsPerEvictionRun;
  private int jdbcMinEvictableIdleTimeMillis;
  private int jdbcDefaultTransactionIsolation;
  private Properties hibernateProperties;

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
    bean.put(Environment.USER, properties.getProperty("jdbc.username"));
    bean.put(Environment.PASS, properties.getProperty("jdbc.password"));
    this.hibernateProperties = bean;
  }

  private void setJdbcProperties(Properties properties) {
    this.jdbcDriverClassName = HibernateConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType);
    this.jdbcValidationQuery = HibernateConstant.JDBC_VALIDATION_QUERY_MAP.get(this.jdbcType);
    this.jdbcUrl = properties.getProperty("jdbc.url");
    this.jdbcUsername = properties.getProperty("jdbc.username");
    this.jdbcPassword = properties.getProperty("jdbc.password");
    this.jdbcMaxActive = Integer.parseInt(properties.getProperty("jdbc.maxActive"));
    this.jdbcMaxWait = Integer.parseInt(properties.getProperty("jdbc.maxWait"));
    this.jdbcMinIdle = Integer.parseInt(properties.getProperty("jdbc.minIdle"));
    this.jdbcInitialSize = Integer.parseInt(properties.getProperty("jdbc.initialSize"));
    this.jdbcTestOnBorrow = Boolean.getBoolean(properties.getProperty("jdbc.testOnBorrow"));
    this.jdbcPoolingStatements = Boolean.getBoolean(properties.getProperty("jdbc.poolingStatements"));
    this.jdbcDefaultAutoCommit = Boolean.getBoolean(properties.getProperty("jdbc.defaultAutoCommit"));
    this.jdbcRemoveAbandoned = Boolean.getBoolean(properties.getProperty("jdbc.removeAbandoned"));
    this.jdbcRemoveAbandonedTimeout = Integer.parseInt(properties.getProperty("jdbc.removeAbandonedTimeout"));
    this.jdbcTestOnReturn = Boolean.getBoolean(properties.getProperty("jdbc.testOnReturn"));
    this.jdbcTestWhileIdle = Boolean.getBoolean(properties.getProperty("jdbc.testWhileIdle"));
    this.jdbcTimeBetweenEvictionRunsMillis = Integer.parseInt(properties.getProperty("jdbc.timeBetweenEvictionRunsMillis"));
    this.jdbcNumTestsPerEvictionRun = Integer.parseInt(properties.getProperty("jdbc.numTestsPerEvictionRun"));
    this.jdbcMinEvictableIdleTimeMillis = Integer.parseInt(properties.getProperty("jdbc.minEvictableIdleTimeMillis"));
    this.jdbcDefaultTransactionIsolation = Integer.parseInt(properties.getProperty("jdbc.defaultTransactionIsolation"));
  }
}
