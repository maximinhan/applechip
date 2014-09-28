package com.applechip.core.configurer;

//@Configuration
//@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.LOWEST_PRECEDENCE)
public class CustomCachingConfigurer {// implements CachingConfigurer {

	// @Autowired
	// private CoreProperties coreProperties;
	//
	// @Override
	// @Bean
	// public CacheManager cacheManager() {
	// return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
	// }
	//
	// @Bean
	// public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
	// EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
	// bean.setShared(coreProperties.isCacheShared());
	// String configLocation = coreProperties.getCacheConfigLocation();
	// if (StringUtil.isNotBlank(configLocation)) {
	// bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
	// }
	// return bean;
	// }
	//
	// @Override
	// @Bean
	// public KeyGenerator keyGenerator() {
	// return new SimpleKeyGenerator();
	// }

}
