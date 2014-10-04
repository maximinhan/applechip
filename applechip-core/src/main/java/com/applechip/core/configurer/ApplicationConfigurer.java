package com.applechip.core.configurer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.constant.SystemConstant;
import com.applechip.core.socket.CustomBaseKeyedPoolableObjectFactory;
import com.applechip.core.socket.CustomSocketClient;
import com.applechip.core.socket.SocketClientUtil;

@Configuration
public class ApplicationConfigurer {

  @Bean
  public SocketClientUtil socketClientUtil() {
    return new SocketClientUtil(new GenericKeyedObjectPool<String, CustomSocketClient>(new CustomBaseKeyedPoolableObjectFactory()));
  }

  @Bean
  public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
    List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
    list.add(this.mappingJackson2HttpMessageConverter());
    list.add(this.jaxb2RootElementHttpMessageConverter());
    list.add(this.byteArrayHttpMessageConverter());
    list.add(this.resourceHttpMessageConverter());
    list.add(this.sourceHttpMessageConverter());
    list.add(this.stringHttpMessageConverter());
    // list.add(new RssChannelHttpMessageConverter());
    // list.add(new AtomFeedHttpMessageConverter());
    bean.setMessageConverters(list);
    return bean;
  }

  @Bean
  public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
    PageableHandlerMethodArgumentResolver bean = new PageableHandlerMethodArgumentResolver();
    bean.setOneIndexedParameters(true);
    return bean;
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    return new MappingJackson2HttpMessageConverter();
  }

  @Bean
  public Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter() {
    return new Jaxb2RootElementHttpMessageConverter();
  }

  @Bean
  public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
    return new ByteArrayHttpMessageConverter();
  }

  @Bean
  public ResourceHttpMessageConverter resourceHttpMessageConverter() {
    return new ResourceHttpMessageConverter();
  }

  @Bean
  public <T extends Source> SourceHttpMessageConverter<T> sourceHttpMessageConverter() {
    return new SourceHttpMessageConverter<T>();
  }

  @Bean
  public StringHttpMessageConverter stringHttpMessageConverter() {
    return new StringHttpMessageConverter(SystemConstant.CHARSET);
  }

  @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
    bean.setBasenames(ApplicationConstant.MessageResource.APPLICATION);
    bean.setUseCodeAsDefaultMessage(true);
    bean.setDefaultEncoding("UTF-8");
    bean.setCacheSeconds(0);
    return bean;
  }

  @Bean
  public MessageSourceAccessor messageSourceAccessor() {
    return new MessageSourceAccessor(this.messageSource());
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new SessionLocaleResolver();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ConfigurableWebBindingInitializer configurableWebBindingInitializer() {
    ConfigurableWebBindingInitializer bean = new ConfigurableWebBindingInitializer();
    // bean.setConversionService();
    // <property name="converters">
    // <list>
    // <bean class="org.anyframe.sample.moviefinder.StringToFilmRatingConverter" />
    // <bean class="org.anyframe.sample.moviefinder.FilmRatingToStringConverter" />
    // </list>
    // </property>
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
    bean.setValidator(localValidatorFactoryBean);
    return bean;
  }

  /*
   * 추가 되어야 할 것 <bean name="handlerAdapter"
   * class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
   * <property name="webBindingInitializer"> <bean
   * class="org.springframework.web.bind.support.ConfigurableWebBindingInitializer"> <property
   * name="conversionService" ref="conversionService"></property> <property name="validator"> <bean
   * class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean"> <property
   * name="providerClass" value="org.hibernate.validator.HibernateValidator"></property> </bean>
   * </property> </bean> </property> <property name="messageConverters"> <list> <bean
   * class="org.springframework.http.converter.ByteArrayHttpMessageConverter"></bean> <bean
   * class="org.springframework.http.converter.StringHttpMessageConverter"></bean> <bean
   * class="org.springframework.http.converter.ResourceHttpMessageConverter"></bean> <bean
   * class="org.springframework.http.converter.xml.SourceHttpMessageConverter"></bean> <bean
   * class="org.springframework.http.converter.xml.XmlAwareFormHttpMessageConverter"></bean> <bean
   * class="org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter"></bean>
   * <bean
   * class="org.springframework.http.converter.json.MappingJacksonHttpMessageConverter"></bean>
   * </list> </property> </bean>
   * 
   * <bean name="handlerMapping"
   * class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
   * <property name="order" value="2"></property> </bean>
   */
}
