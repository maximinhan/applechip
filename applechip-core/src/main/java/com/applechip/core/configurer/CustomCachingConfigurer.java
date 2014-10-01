package com.applechip.core.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

import com.applechip.core.properties.ApplicationProperties;

@Configuration
@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.LOWEST_PRECEDENCE)
public class CustomCachingConfigurer implements CachingConfigurer {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory(this.jedisPoolConfig());
		factory.setHostName(applicationProperties.getRedisHost());
		factory.setPort(applicationProperties.getRedisPort());
		factory.setTimeout(applicationProperties.getRedisTimeout());
		factory.setDatabase(applicationProperties.getRedisDatabase());
		factory.setUsePool(applicationProperties.isRedisUsePool());
		return factory;
	}

	private JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setTestWhileIdle(applicationProperties.isRedisPoolTestWhileIdle());
		jedisPoolConfig.setMinEvictableIdleTimeMillis(applicationProperties.getRedisPoolMinEvictableIdleTimeMillis());
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(applicationProperties
				.getRedisPoolTimeBetweenEvictionRunsMillis());
		jedisPoolConfig.setNumTestsPerEvictionRun(applicationProperties.getRedisPoolNumTestsPerEvictionRun());
		return jedisPoolConfig;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(this.jedisConnectionFactory());
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager() {
		return new RedisCacheManager(this.redisTemplate());
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	//	@Override
	//	@Bean
	//	public CacheManager cacheManager() {
	//		return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
	//	}
	//
	//	@Bean
	//	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
	//		EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
	//		bean.setShared(coreProperties.isCacheShared());
	//		String configLocation = coreProperties.getCacheConfigLocation();
	//		if (StringUtil.isNotBlank(configLocation)) {
	//			bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
	//		}
	//		return bean;
	//	}

}
