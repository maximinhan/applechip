package com.applechip.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import com.applechip.core.CoreConfig;

@Configuration
@ComponentScan(basePackageClasses = { CoreConfig.class, ApiConfig.class }, excludeFilters = @Filter({ Controller.class,
		Configuration.class }))
public class ApiConfig {
}