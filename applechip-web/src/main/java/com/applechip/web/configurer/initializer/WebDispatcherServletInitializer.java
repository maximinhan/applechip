package com.applechip.web.configurer.initializer;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.applechip.web.WebConfig;
import com.applechip.web.configurer.WebWebMvcConfigurer;

public class WebDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  // <listener>
  // <listener-class>org.h2.server.web.DbStarter</listener-class>
  // </listener>

  // @formatter:off
//  @Override
//  public void onStartup(ServletContext servletContext) {
//    XmlWebApplicationContext xmlWebApplicationContext = new XmlWebApplicationContext();
//    xmlWebApplicationContext.setConfigLocation("/WEB-INF/applicationContext.xml");
//    servletContext.addListener(new ContextLoaderListener(xmlWebApplicationContext));
//
//    AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
//    annotationConfigWebApplicationContext.scan("org.example.config");
//    servletContext.addListener(new ContextLoaderListener(annotationConfigWebApplicationContext));
//
//    servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
//    servletContext.setInitParameter("com.sun.faces.expressionFactory", "com.sun.el.ExpressionFactoryImpl");
//    
//    Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(annotationConfigWebApplicationContext));  
//    dynamic.addMapping("/");  
//    dynamic.setLoadOnStartup(1);  
//  }

//  @Override
//  protected void customizeRegistration(Dynamic registration) {
//    registration.setInitParameter("dispatchOptionsRequest", "true");
//  }
  
  
//  EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
//  
//  CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//  characterEncodingFilter.setEncoding("UTF-8");
//  characterEncodingFilter.setForceEncoding(true);
//
//  FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
//  characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
//
//  FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
//  security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
//
//  FilterRegistration.Dynamic sitemesh = servletContext.addFilter("sitemesh", new ConfigurableSiteMeshFilter());
//  sitemesh.addMappingForUrlPatterns(dispatcherTypes, true, "*.jsp");

  // @formatter:on

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // return new Class[] { CoreConfig.class, WebConfig.class };
    return new Class[] {WebConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {WebWebMvcConfigurer.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("UTF-8");
    characterEncodingFilter.setForceEncoding(true);
    return new Filter[] {characterEncodingFilter};
  }
}
