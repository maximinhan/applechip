package com.applechip.web.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.applechip.web.WebConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfig.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
public class WebWebMvcConfigurer extends WebMvcConfigurerAdapter {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.setOrder(-10).addResourceHandler("version.txt").addResourceLocations("version.txt");
		registry.setOrder(-10).addResourceHandler("favicon.ico").addResourceLocations("favicon.ico");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
	}

	@Bean
	public InternalResourceViewResolver jspViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/app/");
		bean.setSuffix(".jsp");
		bean.setOrder(0);
		return bean;
	}
}
