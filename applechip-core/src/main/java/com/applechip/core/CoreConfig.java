package com.applechip.core;

import java.io.IOException;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.applechip.core.exception.SystemException;
import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.properties.DatabaseProperties;
import com.applechip.core.properties.RuntimeProperties;
import com.applechip.core.service.ApplicationService;
import com.applechip.core.util.PropertiesLoaderUtil;

@Slf4j
@Configuration
@ComponentScan(basePackageClasses = { CoreConfig.class })
public class CoreConfig {

	/*
	 * use.. <util:map id="loginCheckErrorCodes" key-type="java.lang.Integer" value-type="java.lang.String"
	 * map-class="java.util.HashMap"> <entry key="3102" value="NET_ERR_SESSION_NOT_FOUND" /> <entry key="3100"
	 * value="NET_ERR_SESSION_TIME_OUT" /> </util:map>
	 */

	@Value("${runtimeProperties}")
	private Resource runtimeProperties;

	@Value("${databaseProperties}")
	private Resource databaseProperties;

	@PostConstruct
	@Bean
	public ApplicationProperties applicationProperties() {
		return new ApplicationProperties();
	}

	@PostConstruct
	@Bean
	public DatabaseProperties databaseProperties() {
		return DatabaseProperties.getInstance(PropertiesLoaderUtil.loadProperty(databaseProperties));
	}

	@Bean
	public RuntimeProperties runtimeProperties() {
		return new RuntimeProperties(this.propertiesConfiguration());
	}

	private PropertiesConfiguration propertiesConfiguration() {
		PropertiesConfiguration propertiesConfiguration = null;
		try {
			propertiesConfiguration = new PropertiesConfiguration(runtimeProperties.getFile());
			propertiesConfiguration.setReloadingStrategy(this.fileChangedReloadingStrategy());
		}
		catch (ConfigurationException e) {
			log.error("propertiesConfiguration create fail... e.getMessage(): {}", e.getMessage());
			throw new SystemException(String.format("propertiesConfiguration create fail... e.getMessage(): %s",
					e.getMessage()), e);
		}
		catch (IOException e) {
			log.error("propertiesConfiguration create fail... e.getMessage(): {}", e.getMessage());
			throw new SystemException(String.format("propertiesConfiguration create fail... e.getMessage(): %s",
					e.getMessage()), e);
		}
		return propertiesConfiguration;
	}

	private FileChangedReloadingStrategy fileChangedReloadingStrategy() {
		FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
		fileChangedReloadingStrategy.setRefreshDelay(this.applicationProperties().getRefreshDelay());
		return fileChangedReloadingStrategy;
	}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CoreConfig.class);
		// applicationContext = new GenericXmlApplicationContext("/META-INF/spring/app-context.xml");

		ApplicationService applicationService = applicationContext.getBean("applicationService",
				ApplicationService.class);
		applicationService.getMessage("");

		// First method execution using key='Josh', not cached
		// System.out.println('message: ' + helloService.getMessage('Josh'));
	}
}
