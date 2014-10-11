package com.applechip.core.configurer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.util.CollectionUtil;

@Configuration
@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.LOWEST_PRECEDENCE)
public class CustomCachingConfigurer implements CachingConfigurer {

  @Autowired
  private ApplicationProperties applicationProperties;

  @Bean
  public CacheManager redisCacheManager() {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    jedisPoolConfig.setTestWhileIdle(applicationProperties.isRedisPoolTestWhileIdle());
    jedisPoolConfig.setMinEvictableIdleTimeMillis(applicationProperties.getRedisPoolMinEvictableIdleTimeMillis());
    jedisPoolConfig.setTimeBetweenEvictionRunsMillis(applicationProperties.getRedisPoolTimeBetweenEvictionRunsMillis());
    jedisPoolConfig.setNumTestsPerEvictionRun(applicationProperties.getRedisPoolNumTestsPerEvictionRun());

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
    jedisConnectionFactory.setHostName(applicationProperties.getRedisHost());
    jedisConnectionFactory.setPort(applicationProperties.getRedisPort());
    jedisConnectionFactory.setTimeout(applicationProperties.getRedisTimeout());
    jedisConnectionFactory.setDatabase(applicationProperties.getRedisDatabase());
    jedisConnectionFactory.setUsePool(applicationProperties.isRedisUsePool());

    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory);
    return new RedisCacheManager(redisTemplate);
  }

  @Bean
  @Override
  public CacheManager cacheManager() {
    List<CacheManager> cacheManagers = new ArrayList<CacheManager>();
    if (applicationProperties.isEhcacheEnabled()) {
      cacheManagers.add(this.ehCacheCacheManager());
    }
    if (applicationProperties.isRedisEnabled()) {
      cacheManagers.add(this.redisCacheManager());
    }

    CompositeCacheManager cacheManager = new CompositeCacheManager();
    cacheManager.setCacheManagers(cacheManagers);
    cacheManager.setFallbackToNoOpCache(Boolean.FALSE);
    return cacheManager;
  }

  @Override
  public KeyGenerator keyGenerator() {
    return new SimpleKeyGenerator();
  }

  @Bean
  public CacheManager ehCacheCacheManager() {
    // EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
    // bean.setShared(applicationProperties.isEhcacheShared());
    // String ehcacheConfigLocation = applicationProperties.getEhcacheConfigLocation();
    // if (StringUtil.isNotBlank(ehcacheConfigLocation)) {
    // bean.setConfigLocation(PropertiesLoaderUtil.getResource(ehcacheConfigLocation));
    // }
    // return new EhCacheCacheManager(bean.getObject());

    // CacheConfiguration cacheConfiguration = new CacheConfiguration();
    // cacheConfiguration.setName(applicationProperties.getEhcacheCacheNames().get(0));
    // cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
    // net.sf.ehcache.config.Configuration configuration = new
    // net.sf.ehcache.config.Configuration();
    // configuration.addCache(cacheConfiguration);
    // return new EhCacheCacheManager(net.sf.ehcache.CacheManager.create(configuration));

    SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
    List<Cache> caches = new ArrayList<Cache>();
    for (String cacheName : applicationProperties.getEhcacheCacheNames()) {
      caches.add(new ConcurrentMapCache(cacheName));
    }
    if (CollectionUtil.isNotEmpty(caches)) {
      simpleCacheManager.setCaches(caches);
    }
    return simpleCacheManager;
  }
}
