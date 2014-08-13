package com.applechip.core;

import java.io.IOException;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.CoreProperties;
import com.applechip.core.properties.HibernateProperties;
import com.applechip.core.properties.RuntimeProperties;
import com.applechip.core.util.PropertiesLoaderUtils;


@Configuration
@ComponentScan(basePackageClasses = {CoreConfig.class})
@Slf4j
public class CoreConfig extends PropertiesLoaderSupport {

  @Value("${runtimeProperties}")
  private Resource runtimeProperties;

  @Value("${coreProperties}")
  private Resource coreProperties;

  @Value("${hibernateProperties}")
  private Resource hibernateProperties;

  @PostConstruct
  @Bean
  public CoreProperties baseProperties() {
    try {
      log.debug("coreProperties path {}", coreProperties.getFile().getAbsolutePath());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return CoreProperties.getProperties(PropertiesLoaderUtils.loadProperties(coreProperties));
  }

  @PostConstruct
  @Bean
  public HibernateProperties hibernateProperties() {
    try {
      log.debug("hibernateProperties path {}", hibernateProperties.getFile().getAbsolutePath());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return HibernateProperties.getProperties(PropertiesLoaderUtils.loadProperties(hibernateProperties));
  }

  @PostConstruct
  @Bean
  public RuntimeProperties runtimeProperties() {
    PropertiesConfiguration bean = null;
    try {
      bean = new PropertiesConfiguration(runtimeProperties.getFile());
      bean.setReloadingStrategy(new FileChangedReloadingStrategy());
    } catch (Exception e) {
      throw new SystemException(e);
    }
    return RuntimeProperties.getProperties(bean);
  }
}
