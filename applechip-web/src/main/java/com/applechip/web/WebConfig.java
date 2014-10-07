package com.applechip.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import com.applechip.core.CoreConfig;
import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.constant.SystemConstant;
import com.applechip.core.util.PropertiesLoaderUtil;

@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class}, excludeFilters = @Filter({Controller.class, Configuration.class}))
@Slf4j
@Import(CoreConfig.class)
public class WebConfig {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
    String[] strings =
        new String[] {ApplicationConstant.PropertiesPath.CONFIG_PROPERTIES, ApplicationConstant.PropertiesPath.APPLICATION_PROPERTIES, ApplicationConstant.PropertiesPath.CONFIG_PROPERTIES_PRODUCTION};
    Resource[] resources = PropertiesLoaderUtil.getResources(strings);
    for (Resource resource : resources) {
      log.debug("target resource... path: {}", resource);
    }
    bean.setValueSeparator("=");
    bean.setLocations(resources);
    bean.setIgnoreResourceNotFound(Boolean.TRUE);
    bean.setIgnoreUnresolvablePlaceholders(Boolean.TRUE);
    bean.setFileEncoding(SystemConstant.CHARSET.toString());
    return bean;
  }
}
