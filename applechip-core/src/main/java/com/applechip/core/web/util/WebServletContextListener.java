package com.applechip.core.web.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.properties.ApplicationProperties;
import com.applechip.core.service.ApplicationService;

public class WebServletContextListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    ServletContext servletContext = servletContextEvent.getServletContext();
    ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    ApplicationService applicationService = applicationContext.getBean(ApplicationService.class);
    ApplicationProperties applicationProperties = applicationContext.getBean(ApplicationProperties.class);
    servletContext.setAttribute(ApplicationConstant.ServerInfo.SERVER_ID, applicationProperties.getServerId());
    servletContext.setAttribute(ApplicationConstant.ServerInfo.GEOIP_GROUP_MAP, applicationService.getGeoipGroupMap());
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
