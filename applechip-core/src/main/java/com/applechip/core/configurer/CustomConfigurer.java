package com.applechip.core.configurer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class CustomConfigurer {

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
		List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
		list.add(this.mappingJackson2HttpMessageConverter());
		list.add(this.stringHttpMessageConverter());
		list.add(this.jaxb2RootElementHttpMessageConverter());
		bean.setMessageConverters(list);
		return bean;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter();
		// bean.
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.APPLICATION_JSON);
		bean.setSupportedMediaTypes(list);
		return bean;
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
		PageableHandlerMethodArgumentResolver bean = new PageableHandlerMethodArgumentResolver();
		bean.setOneIndexedParameters(true);
		return bean;
	}

	@Bean
	public Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter() {
		Jaxb2RootElementHttpMessageConverter bean = new Jaxb2RootElementHttpMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.APPLICATION_XML);
		bean.setSupportedMediaTypes(list);
		return bean;
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter bean = new StringHttpMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.TEXT_PLAIN);
		bean.setSupportedMediaTypes(list);
		return bean;
	}

	@Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
	public MessageSource messageSource() {
		ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
		// ReloadableResourceBundleMessageSource bean = new
		// ReloadableResourceBundleMessageSource();
		bean.setBasenames("message.ApplicationResources");
		bean.setUseCodeAsDefaultMessage(true);
		bean.setDefaultEncoding("UTF-8");
		bean.setCacheSeconds(0);
		return bean;
	}

	/*
	 * 추가 되어야 할 것 <bean name="handlerAdapter"
	 * class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"> <property
	 * name="webBindingInitializer"> <bean
	 * class="org.springframework.web.bind.support.ConfigurableWebBindingInitializer"> <property
	 * name="conversionService" ref="conversionService"></property> <property name="validator"> <bean
	 * class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean"> <property name="providerClass"
	 * value="org.hibernate.validator.HibernateValidator"></property> </bean> </property> </bean> </property> <property
	 * name="messageConverters"> <list> <bean
	 * class="org.springframework.http.converter.ByteArrayHttpMessageConverter"></bean> <bean
	 * class="org.springframework.http.converter.StringHttpMessageConverter"></bean> <bean
	 * class="org.springframework.http.converter.ResourceHttpMessageConverter"></bean> <bean
	 * class="org.springframework.http.converter.xml.SourceHttpMessageConverter"></bean> <bean
	 * class="org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter"></bean> <bean
	 * class="org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter"></bean> <bean
	 * class="org.springframework.http.converter.json.MappingJacksonHttpMessageConverter"></bean> </list> </property>
	 * </bean>
	 * 
	 * <bean name="handlerMapping"
	 * class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"> <property
	 * name="order" value="2"></property> </bean>
	 */
}
