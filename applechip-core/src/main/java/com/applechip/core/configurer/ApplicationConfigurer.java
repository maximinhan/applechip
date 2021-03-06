package com.applechip.core.configurer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.socket.CustomBaseKeyedPoolableObjectFactory;
import com.applechip.core.socket.CustomSocketClient;
import com.applechip.core.socket.SocketClientUtil;
import com.applechip.core.util.ApplicationUtil;
import com.applechip.core.util.GeoipUtil;

@Configuration
public class ApplicationConfigurer {

  @Autowired
  private ApplicationProperties applicationProperties;

  @Bean
  public GeoipUtil geoipUtil() {
    return GeoipUtil.getInstance(applicationProperties.getGeoipFilePath());
  }

  @Bean
  public ApplicationUtil ApplicationUtil() {
    return ApplicationUtil.getInstance(this.messageSourceAccessor());
  }

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
    ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
    reloadableResourceBundleMessageSource.setBasenames(ApplicationConstant.MessageResource.APPLICATION);// i18n
    reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
    reloadableResourceBundleMessageSource.setDefaultEncoding(ApplicationConstant.CHARSET.toString());
    reloadableResourceBundleMessageSource.setCacheSeconds(0);
    return reloadableResourceBundleMessageSource;
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
}
