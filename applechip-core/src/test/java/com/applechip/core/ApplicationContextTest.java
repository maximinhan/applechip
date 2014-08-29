package com.applechip.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.applechip.core.constant.CoreConstant;
import com.applechip.core.util.PropertiesLoaderUtil;

@Configuration
@Import(CoreConfig.class)
public class ApplicationContextTest extends PropertiesLoaderSupport {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
    bean.setLocations(PropertiesLoaderUtil.getResources(CoreConstant.CONFIG_PROPERTIES_PATH_DEV));
    bean.setIgnoreResourceNotFound(Boolean.TRUE);
    bean.setLocalOverride(Boolean.TRUE);
    return bean;
  }
}
