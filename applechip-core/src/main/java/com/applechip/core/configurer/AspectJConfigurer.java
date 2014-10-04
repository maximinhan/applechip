package com.applechip.core.configurer;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.CompositeTransactionAttributeSource;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.applechip.core.configurer.support.AspectJRole;
import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.util.MonitoringInterceptor;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectJConfigurer {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private PlatformTransactionManager annotationDrivenTransactionManager;

	@Bean
	public Advisor transactionAdvisor() {
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression(AspectJRole.class.getCanonicalName() + ".servicePointcut()");
		return new DefaultPointcutAdvisor(aspectJExpressionPointcut, new TransactionInterceptor(
				annotationDrivenTransactionManager, this.transactionAttributeSource()));
	}

	// @Bean
	// public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor() {
	// AspectJExpressionPointcutAdvisor bean = new AspectJExpressionPointcutAdvisor();
	// bean.setExpression(CustomAspectJRoleConfigurer.class.getCanonicalName() + ".servicePointcut()");
	// bean.setAdvice(new TransactionInterceptor(annotationDrivenTransactionManager,
	// this.transactionAttributeSource()));
	// bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
	// return bean;
	// }

	private TransactionAttributeSource transactionAttributeSource() {
		NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
		nameMatchTransactionAttributeSource.setProperties(applicationProperties.getDatabaseTransactionProperties());
		AnnotationTransactionAttributeSource annotationTransactionAttributeSource = new AnnotationTransactionAttributeSource();
		return new CompositeTransactionAttributeSource(new TransactionAttributeSource[] {
				annotationTransactionAttributeSource, nameMatchTransactionAttributeSource });
	}

	@Bean
	public Advisor monitoringAdvisor() {
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression(AspectJRole.class.getCanonicalName() + ".servicePointcut()");
		return new DefaultPointcutAdvisor(aspectJExpressionPointcut, new MonitoringInterceptor(
				applicationProperties.isMonitoringEnabled(), applicationProperties.getMonitoringFrequency(),
				applicationProperties.getMonitoringThreshold()));
	}
}
