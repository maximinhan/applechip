package com.applechip.core;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderSupport;

import com.applechip.core.constant.BaseConstant;
import com.applechip.core.util.PropertiesLoaderUtils;

@Configuration
@Import(CoreConfig.class)
public class ApplicationContextTest extends PropertiesLoaderSupport {

  private final static Log log = LogFactory.getLog(ApplicationContextTest.class);

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Properties properties = PropertiesLoaderUtils.loadProperties(resolver.getResource(BaseConstant.CONFIG_PROPERTIES_PATH_DEV));

    MutablePropertySources sources = new MutablePropertySources();
    sources.addFirst(new PropertiesPropertySource(BaseConstant.CONFIG_PROPERTIES, properties));

    Properties source = (Properties) sources.get(BaseConstant.CONFIG_PROPERTIES).getSource();
    for (String propertyName : source.stringPropertyNames()) {
      if (StringUtils.equals(propertyName, BaseConstant.ENVIRONMENT)) {
        continue;
      }
      Resource resource = resolver.getResource(source.getProperty(propertyName));
      sources.addFirst(new PropertiesPropertySource(propertyName, PropertiesLoaderUtils.loadProperties(resource)));
      log.debug(String.format("property add... name : %s", propertyName));
    }
    PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
    bean.setPropertySources(sources);
    return bean;
  }
}