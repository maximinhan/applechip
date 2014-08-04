package com.applechip.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created with IntelliJ IDEA. User: Yawn Date: 2014. 7. 8. Time: 오후 7:
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.applechip.api"})
public class ApplechipApiApplicationContextConfig extends WebMvcConfigurationSupport {
  @Autowired
  PageableHandlerMethodArgumentResolver pageableResolver;

  @Bean
  public InternalResourceViewResolver jspViewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();
    bean.setPrefix("/WEB-INF/views/");
    bean.setSuffix(".jsp");
    bean.setOrder(0);
    return bean;
  }

  @Bean
  public PageableHandlerMethodArgumentResolver pageableResolver() {
    PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();
    pageableHandlerMethodArgumentResolver.setOneIndexedParameters(true);

    return pageableHandlerMethodArgumentResolver;
  }
}
