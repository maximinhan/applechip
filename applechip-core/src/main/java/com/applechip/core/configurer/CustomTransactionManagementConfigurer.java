package com.applechip.core.configurer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.applechip.core.configurer.support.CustomEventListener;
import com.applechip.core.configurer.support.CustomHibernateJpaVendorAdapter;
import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.DatabaseProperties;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.HIGHEST_PRECEDENCE)
public class CustomTransactionManagementConfigurer implements TransactionManagementConfigurer {

  @Autowired
  private DatabaseProperties databaseProperties;

  @Autowired
  private CustomEventListener entityListener;

  // @Configuration
  // @ComponentScan("springweb.data")
  // public static class RootConfig {
  // @Bean
  // public DataSource dataSsource() {
  // DataSource bean = new
  // EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:schema.sql").ignoreFailedDrops(Boolean.TRUE).build();
  // return bean;
  // }
  // }

  @Override
  @Bean
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    return new JpaTransactionManager(this.entityManagerFactory());
  }

  @PostConstruct
  public void eventListenerRegistry() {
    HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) this.entityManagerFactory();
    SessionFactory sessionFactory = hibernateEntityManagerFactory.getSessionFactory();
    EventListenerRegistry eventListenerRegistry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);
    eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(entityListener);
    eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(entityListener);
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    CustomHibernateJpaVendorAdapter customHibernateJpaVendorAdapter = CustomHibernateJpaVendorAdapter.getInstance();
    customHibernateJpaVendorAdapter.setGenerateDdl(Boolean.TRUE);
    customHibernateJpaVendorAdapter.setShowSql(Boolean.TRUE);
    customHibernateJpaVendorAdapter.setDatabase(Database.valueOf(databaseProperties.getType()));
    localContainerEntityManagerFactoryBean.setJpaVendorAdapter(customHibernateJpaVendorAdapter);
    localContainerEntityManagerFactoryBean.setDataSource(this.dataSource());
    localContainerEntityManagerFactoryBean.setJpaProperties(databaseProperties.getHibernateProperties());
    localContainerEntityManagerFactoryBean.setPersistenceUnitName(ApplicationConstant.PERSISTENCE_UNIT_NAME);
    localContainerEntityManagerFactoryBean.setPackagesToScan(databaseProperties.getPackagesToScan());
    // localContainerEntityManagerFactoryBean.setLoadTimeWeaver(new
    // InstrumentationLoadTimeWeaver());
    // localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
    localContainerEntityManagerFactoryBean.afterPropertiesSet();
    return localContainerEntityManagerFactoryBean.getObject();
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
    DataSource dataSource = null;
    try {
      dataSource = BasicDataSourceFactory.createDataSource(databaseProperties.getDataSourceProperties());
    } catch (Exception e) {
      throw new SystemException(String.format("dataSource create fail.. %s", e.getMessage()), e);
    }
    return dataSource;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

}
