package com.applechip.core.configurer;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.aopalliance.aop.Advice;
import org.apache.commons.dbcp.BasicDataSourceFactory;
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
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.CompositeTransactionAttributeSource;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.applechip.core.constant.CoreConstant;
import com.applechip.core.entity.User;
import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.DatabaseProperties;
import com.applechip.core.repository.CustomHibernateJpaVendorAdapter;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.HIGHEST_PRECEDENCE)
public class CustomTransactionManagementConfigurer implements TransactionManagementConfigurer {

  @Autowired
  private DatabaseProperties databaseProperties;

  @Override
  @Bean
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    PlatformTransactionManager bean = null;
    try {
      bean = new JpaTransactionManager(this.entityManagerFactory());
    } catch (Exception e) {
      throw new SystemException(String.format("annotationDrivenTransactionManager create fail.. %s", e), e);
    }
    return bean;
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {
    EntityManagerFactory bean = null;
    try {
      LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
      localContainerEntityManagerFactoryBean.setJpaVendorAdapter(CustomHibernateJpaVendorAdapter.getInstance());
      localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
      localContainerEntityManagerFactoryBean.setJpaProperties(databaseProperties.getHibernateProperties());
      localContainerEntityManagerFactoryBean.setPersistenceUnitName(CoreConstant.PERSISTENCE_UNIT_NAME);
      localContainerEntityManagerFactoryBean.setPackagesToScan(User.class.getPackage().getName());
      localContainerEntityManagerFactoryBean.afterPropertiesSet();
      bean = localContainerEntityManagerFactoryBean.getObject();
    } catch (Exception e) {
      throw new SystemException(String.format("entityManagerFactory create fail.. %s", e), e);
    }
    return bean;
  }

  // @Bean(name = "jpaVendorAdapter")
  // public JpaVendorAdapter jpaVendorAdapter() {
  // CustomHibernateJpaVendorAdapter jpaVendorAdapter = new CustomHibernateJpaVendorAdapter();
  // jpaVendorAdapter.setShowSql(true);
  // jpaVendorAdapter.setDatabase(Database.ORACLE);
  // jpaVendorAdapter.setDatabasePlatform(Oracle10gDialect.class.getName());
  // jpaVendorAdapter.setGenerateDdl(false);
  // return jpaVendorAdapter;
  // }

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    DataSource bean = null;
    try {
      bean = BasicDataSourceFactory.createDataSource(databaseProperties.getDataSourceProperties());
    } catch (Exception e) {
      throw new SystemException(String.format("dataSource create fail.. %s", e), e);
    }
    return bean;
  }

  @Bean
  public Advice advice() {
    Advice bean = null;
    try {
      bean = new TransactionInterceptor(this.annotationDrivenTransactionManager(), this.transactionAttributeSource());
    } catch (Exception e) {
      throw new SystemException(String.format("advice create fail.. %s", e), e);
    }
    return bean;
  }

  private TransactionAttributeSource transactionAttributeSource() {
    TransactionAttributeSource bean = null;
    try {
      NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
      nameMatchTransactionAttributeSource.setProperties(databaseProperties.getTransactionProperties());
      AnnotationTransactionAttributeSource annotationTransactionAttributeSource = new AnnotationTransactionAttributeSource();
      bean = new CompositeTransactionAttributeSource(new TransactionAttributeSource[] {annotationTransactionAttributeSource, nameMatchTransactionAttributeSource});
    } catch (Exception e) {
      throw new SystemException(String.format("transactionAttributeSource create fail.. %s", e), e);
    }
    return bean;
  }

  @Bean
  public AspectJExpressionPointcutAdvisor advisorManager() {
    AspectJExpressionPointcutAdvisor bean = null;
    try {
      bean = new AspectJExpressionPointcutAdvisor();
      // bean.setExpression("execution(* *..service.*Manager.*(..))");
      bean.setExpression("execution(* com..*.*Service.*(..))");
      bean.setAdvice(this.advice());
      bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    } catch (Exception e) {
      throw new SystemException(String.format("advisorManager create fail.. %s", e), e);
    }
    return bean;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

}
