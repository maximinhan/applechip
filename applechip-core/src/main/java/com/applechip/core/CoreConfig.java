package com.applechip.core;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.BaseProperties;
import com.applechip.core.properties.HibernateProperties;
import com.applechip.core.properties.RuntimeProperties;
import com.applechip.core.util.PropertiesLoaderUtils;


@Configuration
@ComponentScan(basePackageClasses = {CoreConfig.class})
public class CoreConfig extends PropertiesLoaderSupport {

  @Value("${runtimeProperties}")
  private Resource runtimeProperties;

  @Value("${baseProperties}")
  private Resource baseProperties;

  @Value("${hibernateProperties}")
  private Resource hibernateProperties;

  @PostConstruct
  @Bean
  public BaseProperties baseProperties() {
    return new BaseProperties(PropertiesLoaderUtils.loadProperties(baseProperties));
  }

  @PostConstruct
  @Bean
  public HibernateProperties databaseProperties() {
    return new HibernateProperties(PropertiesLoaderUtils.loadProperties(hibernateProperties));
  }

  @PostConstruct
  @Bean
  public RuntimeProperties runtimeProperties() {
    PropertiesConfiguration bean = null;
    try {
      bean = new PropertiesConfiguration(runtimeProperties.getFile());
    } catch (Exception e) {
      throw new SystemException(e);
    }
    bean.setReloadingStrategy(new FileChangedReloadingStrategy());
    return new RuntimeProperties(bean);
  }
}
