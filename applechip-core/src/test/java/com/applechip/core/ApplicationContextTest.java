package com.applechip.core;

import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.applechip.core.constant.CoreConstant;
import com.applechip.core.util.PropertiesLoaderUtils;

@Configuration
@Import(CoreConfig.class)
@Slf4j
public class ApplicationContextTest extends PropertiesLoaderSupport {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Properties properties = PropertiesLoaderUtils.loadProperties(resolver.getResource(CoreConstant.CONFIG_PROPERTIES_PATH_DEV));

    MutablePropertySources sources = new MutablePropertySources();
    sources.addFirst(new PropertiesPropertySource(CoreConstant.CONFIG_PROPERTIES, properties));

    Properties source = (Properties) sources.get(CoreConstant.CONFIG_PROPERTIES).getSource();
    for (String propertyName : source.stringPropertyNames()) {
      if (StringUtils.equals(propertyName, CoreConstant.ENVIRONMENT)) {
        continue;
      }
      Resource resource = resolver.getResource(source.getProperty(propertyName));
      sources.addFirst(new PropertiesPropertySource(propertyName, PropertiesLoaderUtils.loadProperties(resource)));
      log.debug("property add... name: {}, path: {}", propertyName, resource.getFilename());
    }
    PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
    bean.setPropertySources(sources);
    return bean;
  }
}