package com.applechip.core.configurer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
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

import com.applechip.core.configurer.support.CustomEventListener;
import com.applechip.core.configurer.support.CustomHibernateJpaVendorAdapter;
import com.applechip.core.constant.CoreConstant;
import com.applechip.core.entity.User;
import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.DatabaseProperties;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.HIGHEST_PRECEDENCE)
public class CustomTransactionManagementConfigurer implements TransactionManagementConfigurer {

	@Autowired
	private DatabaseProperties databaseProperties;

	@Autowired
	private CustomEventListener entityListener;

	// <bean id="performanceMonitor" class="com.applechip.core.util.PerformanceMonitoringInterceptor">
	// <property name="systemName" value="PA" />
	// <property name="enabled" value="true" />
	// <property name="statLogFrequency" value="10" />
	// <property name="warningThreshold" value="3000" />
	// </bean>
	// <aop:config>
	// <aop:advisor id="managerPerf" advice-ref="performanceMonitor" pointcut="execution(* com..*.*Manager.*(..))"
	// order="2" />
	// <aop:advisor id="servicePerf" advice-ref="performanceMonitor" pointcut="execution(* com..*.*Service.*(..))"
	// order="3" />
	// </aop:config>

	@Override
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		PlatformTransactionManager bean = null;
		try {
			bean = new JpaTransactionManager(this.entityManagerFactory());
		}
		catch (Exception e) {
			throw new SystemException(e, "annotationDrivenTransactionManager create fail.. %s", e.getMessage());
		}
		return bean;
	}

	@PostConstruct
	public void eventListenerRegistry() {
		HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) this
				.entityManagerFactory();
		SessionFactory sessionFactory = hibernateEntityManagerFactory.getSessionFactory();
		EventListenerRegistry eventListenerRegistry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry()
				.getService(EventListenerRegistry.class);
		eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(entityListener);
		eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(entityListener);
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
			localContainerEntityManagerFactoryBean.setPackagesToScan(databaseProperties.getPackagesToScan());
			// <property name="persistenceProviderClass" value="org.hibernate.ejb.HibernatePersistence" />
			// <property name="loadTimeWeaver">
			// <bean class="org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver" />
			// </property>
			localContainerEntityManagerFactoryBean.afterPropertiesSet();
			bean = localContainerEntityManagerFactoryBean.getObject();
		}
		catch (Exception e) {
			throw new SystemException(e, "entityManagerFactory create fail.. %s", e.getMessage());
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
		}
		catch (Exception e) {
			throw new SystemException(e, "entityManagerFactory create fail.. %s", e.getMessage());
		}
		return bean;
	}

	@Bean
	public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor() {
		AspectJExpressionPointcutAdvisor bean = null;
		try {
			bean = new AspectJExpressionPointcutAdvisor();
			// bean.setExpression("execution(* *..service.*Manager.*(..))");
			bean.setExpression("execution(* com..*.*Service.*(..))");
			bean.setAdvice(new TransactionInterceptor(this.annotationDrivenTransactionManager(), this
					.transactionAttributeSource()));
			bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		}
		catch (Exception e) {
			throw new SystemException(e, "advisorManager create fail.. %s", e.getMessage());
		}
		return bean;
	}

	private TransactionAttributeSource transactionAttributeSource() {
		TransactionAttributeSource bean = null;
		try {
			NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
			nameMatchTransactionAttributeSource.setProperties(databaseProperties.getTransactionProperties());
			AnnotationTransactionAttributeSource annotationTransactionAttributeSource = new AnnotationTransactionAttributeSource();
			bean = new CompositeTransactionAttributeSource(new TransactionAttributeSource[] {
					annotationTransactionAttributeSource, nameMatchTransactionAttributeSource });
		}
		catch (Exception e) {
			throw new SystemException(e, "transactionAttributeSource create fail.. %s", e.getMessage());
		}
		return bean;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
