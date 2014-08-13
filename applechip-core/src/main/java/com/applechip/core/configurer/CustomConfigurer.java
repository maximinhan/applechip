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
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

@Configuration
public class CustomConfigurer {

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter();
    List<MediaType> list = new ArrayList<MediaType>();
    list.add(MediaType.APPLICATION_JSON);
    bean.setSupportedMediaTypes(list);
    // org.hibernate.cfg.Configuration
    // SchemaExport
    // Class.forName(className)
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

}
