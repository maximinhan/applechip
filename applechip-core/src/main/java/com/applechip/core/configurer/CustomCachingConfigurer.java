package com.applechip.core.configurer;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.applechip.core.properties.BaseProperties;


@Configuration
@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.LOWEST_PRECEDENCE)
public class CustomCachingConfigurer implements CachingConfigurer {

  @Autowired
  private BaseProperties baseProperties;

  @Override
  @Bean
  public CacheManager cacheManager() {
    return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
  }

  @Bean
  public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
    EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
    bean.setShared(baseProperties.isCacheShared());
    String configLocation = baseProperties.getCacheConfigLocation();
    if (StringUtils.isNotBlank(configLocation)) {
      bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
    }
    return bean;
  }

  @Override
  @Bean
  public KeyGenerator keyGenerator() {
    return new SimpleKeyGenerator();
  }

}
