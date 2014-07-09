package com.applechip.core.configurer;

import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Environment;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.CompositeTransactionAttributeSource;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.applechip.core.constant.BaseConstant;
import com.applechip.core.entity.User;
import com.applechip.core.properties.HibernateProperties;
import com.applechip.core.repository.CustomHibernateJpaVendorAdapter;
import com.applechip.core.util.CryptoUtil;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.HIGHEST_PRECEDENCE)
public class CustomTransactionManagementConfigurer implements TransactionManagementConfigurer {

  private final static Log log = LogFactory.getLog(CustomTransactionManagementConfigurer.class);

  @Autowired
  private HibernateProperties hibernateProperties;

  @Autowired
  private CryptoUtil cryptUtil;

  @Override
  @Bean
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    log.debug("annotationDrivenTransactionManager create...");
    return new JpaTransactionManager(this.entityManagerFactory());
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setJpaVendorAdapter(new CustomHibernateJpaVendorAdapter());
    bean.setDataSource(this.dataSource());
    bean.setJpaProperties(this.jpaProperties());
    bean.setPersistenceUnitName(BaseConstant.PERSISTENCE_UNIT_NAME);
    bean.setPackagesToScan(User.class.getPackage().getName());
    bean.afterPropertiesSet();
    log.debug("entityManagerFactory create...");
    return bean.getObject();
  }

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    BasicDataSource bean = new BasicDataSource();
    bean.setDriverClassName(hibernateProperties.getJdbcDriverClassName());
    bean.setUrl(hibernateProperties.getJdbcUrl());
    bean.setUsername(hibernateProperties.getJdbcUsername());
    bean.setPassword(this.decrypt(hibernateProperties.getJdbcPassword()));
    bean.setValidationQuery(hibernateProperties.getJdbcValidationQuery());
    bean.setMaxActive(hibernateProperties.getJdbcMaxActive());
    bean.setMaxWait(hibernateProperties.getJdbcMaxWait());
    bean.setMinIdle(hibernateProperties.getJdbcMinIdle());
    bean.setInitialSize(hibernateProperties.getJdbcInitialSize());
    bean.setTestOnBorrow(hibernateProperties.isJdbcTestOnBorrow());
    bean.setPoolPreparedStatements(hibernateProperties.isJdbcPoolingStatements());
    bean.setDefaultAutoCommit(hibernateProperties.isJdbcDefaultAutoCommit());
    bean.setRemoveAbandoned(hibernateProperties.isJdbcRemoveAbandoned());
    bean.setRemoveAbandonedTimeout(hibernateProperties.getJdbcRemoveAbandonedTimeout());
    bean.setTestOnReturn(hibernateProperties.isJdbcTestOnReturn());
    bean.setTestWhileIdle(hibernateProperties.isJdbcTestWhileIdle());
    bean.setTimeBetweenEvictionRunsMillis(hibernateProperties.getJdbcTimeBetweenEvictionRunsMillis());
    bean.setNumTestsPerEvictionRun(hibernateProperties.getJdbcNumTestsPerEvictionRun());
    bean.setMinEvictableIdleTimeMillis(hibernateProperties.getJdbcMinEvictableIdleTimeMillis());
    bean.setDefaultTransactionIsolation(hibernateProperties.getJdbcDefaultTransactionIsolation());
    log.debug("dataSource create...");
    return bean;
  }

  private String decrypt(String str) {
    try {
      return cryptUtil.decrypt(str);
    } catch (Exception e) {
      log.warn("password is not encrypt..." + UUID.randomUUID().toString());
      return str;
    }
  }

  private Properties jpaProperties() {
    Properties bean = new Properties();
    bean.put(Environment.DIALECT, hibernateProperties.getHibernateDialect());
    bean.put(Environment.SHOW_SQL, hibernateProperties.getHibernateShowSql());
    bean.put(Environment.FORMAT_SQL, hibernateProperties.getHibernateFormatSql());
    bean.put(Environment.USE_SQL_COMMENTS, hibernateProperties.getHibernateUseSqlComments());
    bean.put(Environment.QUERY_SUBSTITUTIONS, hibernateProperties.getHibernateQuerySubstitutions());
    bean.put(Environment.HBM2DDL_AUTO, hibernateProperties.getHibernateHbm2ddlAuto());
    bean.put(Environment.USE_QUERY_CACHE, hibernateProperties.getHibernateUseQueryCache());
    bean.put(Environment.USE_SECOND_LEVEL_CACHE, hibernateProperties.getHibernateUseSecondLevelCache());
    bean.put(Environment.CACHE_REGION_FACTORY, hibernateProperties.getHibernateCacheRegionFactory());
    bean.put(Environment.CACHE_PROVIDER_CONFIG, hibernateProperties.getHibernateCacheProviderConfig());
    return bean;
  }

  @Bean
  public TransactionInterceptor transactionInterceptor() {
    TransactionInterceptor bean = new TransactionInterceptor();
    bean.setTransactionAttributeSource(this.transactionAttributeSource());
    bean.setTransactionManager(this.annotationDrivenTransactionManager());
    log.debug("transactionInterceptor create...");
    return bean;
  }

  private TransactionAttributeSource transactionAttributeSource() {
    NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
    nameMatchTransactionAttributeSource.setProperties(this.transactionAttributes());
    AnnotationTransactionAttributeSource annotationTransactionAttributeSource = new AnnotationTransactionAttributeSource();
    TransactionAttributeSource transactionAttributeSource =
        new CompositeTransactionAttributeSource(new TransactionAttributeSource[] {annotationTransactionAttributeSource, nameMatchTransactionAttributeSource});
    log.debug("transactionAttributeSource create...");
    return transactionAttributeSource;
  }

  private Properties transactionAttributes() {
    Properties transactionAttributes = new Properties();
    transactionAttributes.setProperty("exist*", "PROPAGATION_REQUIRED,readOnly");// ISOLATION_READ_UNCOMMITTED,timeout_30,-Exception
    transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
    transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");
    return transactionAttributes;
  }

  @Bean
  public AspectJExpressionPointcutAdvisor advisorManager() {
    AspectJExpressionPointcutAdvisor bean = new AspectJExpressionPointcutAdvisor();
    // bean.setExpression("execution(* *..service.*Manager.*(..))");
    bean.setExpression("execution(* com..*.*Manager.*(..))");
    // bean.setExpression("execution(* com..*.*Service.*(..))");
    bean.setAdvice(this.transactionInterceptor());
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    log.debug("transactionAttributeSource create...");
    return bean;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
