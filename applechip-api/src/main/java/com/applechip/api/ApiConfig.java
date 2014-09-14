package com.applechip.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = { ApiConfig.class }, excludeFilters = @Filter({ Controller.class,
		Configuration.class }))
//@Import(CoreConfig.class)
public class ApiConfig {
}