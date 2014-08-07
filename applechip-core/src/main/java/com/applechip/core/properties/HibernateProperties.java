package com.applechip.core.properties;

import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

import com.applechip.core.constant.BaseConstant;

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

  public static HibernateProperties getProperties(Properties properties) {
    return new HibernateProperties(properties);
  }

  private HibernateProperties(Properties properties) {
    this.jdbcType = properties.getProperty("jdbc.type");
    this.setJdbcProperties(properties);
    this.setHibernateProperties(properties);
  }

  private void setHibernateProperties(Properties properties) {
    this.hibernateDialect = BaseConstant.hibernateDialectMap.get(this.jdbcType);
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

  private void setJdbcProperties(Properties properties) {
    this.jdbcDriverClassName = BaseConstant.jdbcDriverClassMap.get(this.jdbcType);
    this.jdbcValidationQuery = BaseConstant.jdbcValidationQueryMap.get(this.jdbcType);
    this.jdbcUrl = properties.getProperty("jdbc.url");
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
