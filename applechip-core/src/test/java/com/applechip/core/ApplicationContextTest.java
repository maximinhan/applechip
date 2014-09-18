package com.applechip.core;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.constant.SystemConstant;
import com.applechip.core.util.PropertiesLoaderUtil;

@Slf4j
@Configuration
@Import(CoreConfig.class)
public class ApplicationContextTest {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = PropertiesLoaderUtil.getResources(ApplicationConstant.CONFIG_PROPERTIES_PATH,
				ApplicationConstant.APPLICATION_PROPERTIES_PATH);
		for (Resource resource : resources) {
			log.debug("target resource... path: {}", resource);
		}
		bean.setLocations(resources);
		bean.setIgnoreResourceNotFound(Boolean.TRUE);
		bean.setIgnoreUnresolvablePlaceholders(Boolean.TRUE);
		bean.setFileEncoding(SystemConstant.CHARSET.toString());
		return bean;
	}
}
