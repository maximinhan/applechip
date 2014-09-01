package com.applechip.core;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.CoreProperties;
import com.applechip.core.properties.DatabaseProperties;
import com.applechip.core.properties.RuntimeProperties;
import com.applechip.core.util.PropertiesLoaderUtil;


@Configuration
@ComponentScan(basePackageClasses = {CoreConfig.class})
public class CoreConfig {

  @Value("${runtimeProperties}")
  private Resource runtimeProperties;

  @Value("${coreProperties}")
  private Resource coreProperties;

  @Value("${databaseProperties}")
  private Resource hibernateProperties;

  @PostConstruct
  @Bean
  public CoreProperties baseProperties() {
    return CoreProperties.getInstance(PropertiesLoaderUtil.loadProperties(coreProperties));
  }

  @PostConstruct
  @Bean
  public DatabaseProperties databaseProperties() {
    return DatabaseProperties.getInstance(PropertiesLoaderUtil.loadProperties(hibernateProperties));
  }

  @PostConstruct
  @Bean
  public RuntimeProperties runtimeProperties() {
    PropertiesConfiguration bean = null;
    File file = null;
    try {
      file = runtimeProperties.getFile();
      bean = new PropertiesConfiguration(file);
      bean.setReloadingStrategy(new FileChangedReloadingStrategy());
    } catch (ConfigurationException e) {
      throw new SystemException(e, "runtimeProperties create fail... path: %s, message: %s", file.getPath(), e.getMessage());
    } catch (IOException e) {
      throw new SystemException(e, "runtimeProperties create fail... filename: %s, message: %s", runtimeProperties.getFilename(), e.getMessage());
    }
    return RuntimeProperties.getInstance(bean);
  }
}
