package com.applechip.core;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExportTask;
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
//    SchemaExportTask
//    org.hibernate.cfg.Configuration
    return CoreProperties.getProperties(PropertiesLoaderUtils.loadProperties(coreProperties));
  }

  @PostConstruct
  @Bean
  public HibernateProperties databaseProperties() {
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
