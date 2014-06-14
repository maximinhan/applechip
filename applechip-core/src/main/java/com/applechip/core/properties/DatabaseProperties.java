package com.applechip.core.properties;

import java.util.Properties;

public class DatabaseProperties {
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
  private String hibernateDialect;
  private String hibernateShowSql;
  private String hibernateFormatSql;
  private String hibernateUseSqlComments;
  private String hibernateQuerySubstitutions;
  private String hibernateHbm2ddlAuto;
  private String hibernateUseQueryCache;
  private String hibernateUseSecondLevelCache;
  private String hibernateCacheRegionFactory;
  private String hibernateCacheProviderConfig;

  public DatabaseProperties(Properties properties) {

    this.jdbcDriverClassName = properties.getProperty("jdbc.driverClassName");
    this.jdbcUrl = properties.getProperty("jdbc.url");
    this.jdbcUsername = properties.getProperty("jdbc.username");
    this.jdbcPassword = properties.getProperty("jdbc.password");
    this.jdbcValidationQuery = properties.getProperty("jdbc.validationQuery");
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
    this.hibernateDialect = properties.getProperty("hibernate.dialect");
    this.hibernateShowSql = properties.getProperty("hibernate.show_sql");
    this.hibernateFormatSql = properties.getProperty("hibernate.format_sql");
    this.hibernateUseSqlComments = properties.getProperty("hibernate.use_sql_comments");
    this.hibernateQuerySubstitutions = properties.getProperty("hibernate.query.substitutions");
    this.hibernateHbm2ddlAuto = properties.getProperty("hibernate.hbm2ddl.auto");
    this.hibernateUseQueryCache = properties.getProperty("hibernate.cache.use_query_cache");
    this.hibernateUseSecondLevelCache = properties.getProperty("hibernate.cache.use_second_level_cache");
    this.hibernateCacheRegionFactory = properties.getProperty("hibernate.cache.region.factory_class");
    this.hibernateCacheProviderConfig = properties.getProperty("hibernate.cache.provider_configuration_file_resource_path");
  }

  public String getJdbcDriverClassName() {
    return jdbcDriverClassName;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getJdbcUsername() {
    return jdbcUsername;
  }

  public String getJdbcPassword() {
    return jdbcPassword;
  }

  public String getJdbcValidationQuery() {
    return jdbcValidationQuery;
  }

  public int getJdbcMaxActive() {
    return jdbcMaxActive;
  }

  public int getJdbcMaxWait() {
    return jdbcMaxWait;
  }

  public int getJdbcMinIdle() {
    return jdbcMinIdle;
  }

  public int getJdbcInitialSize() {
    return jdbcInitialSize;
  }

  public boolean isJdbcTestOnBorrow() {
    return jdbcTestOnBorrow;
  }

  public boolean isJdbcPoolingStatements() {
    return jdbcPoolingStatements;
  }

  public boolean isJdbcDefaultAutoCommit() {
    return jdbcDefaultAutoCommit;
  }

  public boolean isJdbcRemoveAbandoned() {
    return jdbcRemoveAbandoned;
  }

  public int getJdbcRemoveAbandonedTimeout() {
    return jdbcRemoveAbandonedTimeout;
  }

  public boolean isJdbcTestOnReturn() {
    return jdbcTestOnReturn;
  }

  public boolean isJdbcTestWhileIdle() {
    return jdbcTestWhileIdle;
  }

  public int getJdbcTimeBetweenEvictionRunsMillis() {
    return jdbcTimeBetweenEvictionRunsMillis;
  }

  public int getJdbcNumTestsPerEvictionRun() {
    return jdbcNumTestsPerEvictionRun;
  }

  public int getJdbcMinEvictableIdleTimeMillis() {
    return jdbcMinEvictableIdleTimeMillis;
  }

  public int getJdbcDefaultTransactionIsolation() {
    return jdbcDefaultTransactionIsolation;
  }

  public String getHibernateDialect() {
    return hibernateDialect;
  }

  public String getHibernateShowSql() {
    return hibernateShowSql;
  }

  public String getHibernateFormatSql() {
    return hibernateFormatSql;
  }

  public String getHibernateUseSqlComments() {
    return hibernateUseSqlComments;
  }

  public String getHibernateQuerySubstitutions() {
    return hibernateQuerySubstitutions;
  }

  public String getHibernateHbm2ddlAuto() {
    return hibernateHbm2ddlAuto;
  }

  public String getHibernateUseQueryCache() {
    return hibernateUseQueryCache;
  }

  public String getHibernateUseSecondLevelCache() {
    return hibernateUseSecondLevelCache;
  }

  public String getHibernateCacheRegionFactory() {
    return hibernateCacheRegionFactory;
  }

  public String getHibernateCacheProviderConfig() {
    return hibernateCacheProviderConfig;
  }

}
