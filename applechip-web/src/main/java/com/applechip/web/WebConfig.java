package com.applechip.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class}, excludeFilters = @Filter({Controller.class, Configuration.class}))
public class WebConfig {
}
