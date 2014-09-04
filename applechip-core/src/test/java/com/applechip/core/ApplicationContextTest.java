package com.applechip.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.applechip.core.constant.CoreConstant;
import com.applechip.core.util.PropertiesLoaderUtil;

@Configuration
@Import(CoreConfig.class)
public class ApplicationContextTest {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
    bean.setLocations(PropertiesLoaderUtil.getResources(CoreConstant.CONFIG_PROPERTIES_DEVELOPMENT));
    bean.setIgnoreResourceNotFound(Boolean.TRUE);
    bean.setFileEncoding(CoreConstant.CHARSET_TO_STRING);
    return bean;
  }
}
