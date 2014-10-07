package com.applechip.core;

import java.io.IOException;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.properties.DatabaseProperties;
import com.applechip.core.properties.RuntimeProperties;
import com.applechip.core.service.ApplicationService;
import com.applechip.core.util.PropertiesLoaderUtil;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = {CoreConfig.class})
public class CoreConfig {

  @Value("${runtimeProperties}")
  private String runtimeProperties;

  @Value("${databaseProperties}")
  private String databaseProperties;

  @PostConstruct
  @Bean
  public ApplicationProperties applicationProperties() {
    return new ApplicationProperties();
  }

  @PostConstruct
  @Bean
  public DatabaseProperties databaseProperties() {
    return new DatabaseProperties(PropertiesLoaderUtil.getProperties(this.databaseProperties));
  }

  @Bean
  public RuntimeProperties runtimeProperties() {
    return new RuntimeProperties(this.propertiesConfiguration());
  }

  private PropertiesConfiguration propertiesConfiguration() {
    PropertiesConfiguration propertiesConfiguration = null;
    try {
      propertiesConfiguration = new PropertiesConfiguration(this.runtimeProperties);
      propertiesConfiguration.setReloadingStrategy(this.fileChangedReloadingStrategy());
    } catch (ConfigurationException e) {
      log.error("propertiesConfiguration create fail... e.getMessage(): {}", e.getMessage());
      propertiesConfiguration = new PropertiesConfiguration();
    }
    return propertiesConfiguration;
  }

  private FileChangedReloadingStrategy fileChangedReloadingStrategy() {
    FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
    fileChangedReloadingStrategy.setRefreshDelay(this.applicationProperties().getRefreshDelay());
    return fileChangedReloadingStrategy;
  }

  public static void main(String[] args) throws IOException {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(CoreConfig.class);
    for (Resource resource : PropertiesLoaderUtil.getResources(ApplicationConstant.PropertiesPath.CONFIG_PROPERTIES, ApplicationConstant.PropertiesPath.APPLICATION_PROPERTIES)) {
      annotationConfigApplicationContext.getEnvironment().getPropertySources().addFirst(new ResourcePropertySource(resource));
    }
    annotationConfigApplicationContext.getBean(ApplicationService.class).getCategories();
  }
}
