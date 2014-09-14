package com.applechip.configurer.initializer;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.applechip.api.ApiConfig;
import com.applechip.configurer.ApplechipApiApplicationContextConfig;

@Order(1)
public class ApplechipApiApplicationInitailizer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApiConfig.class };
		// return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ApplechipApiApplicationContextConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
