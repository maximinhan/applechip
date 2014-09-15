package com.applechip.core;

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
@ComponentScan(basePackageClasses = { CoreConfig.class })
public class CoreConfig {

	/*
	 * use.. <util:map id="loginCheckErrorCodes" key-type="java.lang.Integer" value-type="java.lang.String"
	 * map-class="java.util.HashMap"> <entry key="3102" value="NET_ERR_SESSION_NOT_FOUND" /> <entry key="3100"
	 * value="NET_ERR_SESSION_TIME_OUT" /> </util:map>
	 */

	@Value("${runtimeProperties}")
	private Resource runtimeProperties;

	@Value("${coreProperties}")
	private Resource coreProperties;

	@Value("${databaseProperties}")
	private Resource databaseProperties;

	@PostConstruct
	@Bean
	public CoreProperties coreProperties() {
		return new CoreProperties(PropertiesLoaderUtil.loadProperties(coreProperties));
	}

	@PostConstruct
	@Bean
	public DatabaseProperties databaseProperties() {
		return DatabaseProperties.getInstance(PropertiesLoaderUtil.loadProperties(databaseProperties));
	}

	// @PostConstruct
	@Bean
	public PropertiesConfiguration propertiesConfiguration() {
		PropertiesConfiguration propertiesConfiguration = null;
		try {
			propertiesConfiguration = new PropertiesConfiguration(runtimeProperties.getFile());
			propertiesConfiguration.setReloadingStrategy(this.fileChangedReloadingStrategy());
		}
		catch (ConfigurationException e) {
			throw new SystemException(e, "runtimeProperties create fail... message: %s", e.getMessage());
		}
		catch (IOException e) {
			throw new SystemException(e, "runtimeProperties create fail... filename: %s, message: %s",
					runtimeProperties.getFilename(), e.getMessage());
		}
		return propertiesConfiguration;
	}

	private FileChangedReloadingStrategy fileChangedReloadingStrategy() {
		FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
		fileChangedReloadingStrategy.setRefreshDelay(this.coreProperties().getRefreshDelay());
		return fileChangedReloadingStrategy;
	}

	@Bean
	public RuntimeProperties runtimeProperties() {
		RuntimeProperties runtimeProperties = new RuntimeProperties(
				PropertiesLoaderUtil.loadProperties(coreProperties), this.propertiesConfiguration());
		return runtimeProperties;
	}
}
