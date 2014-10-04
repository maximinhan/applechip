package com.applechip.core.web.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.util.WebUtil;

@Slf4j
public class HeaderFilter implements Filter {

  private FilterConfig filterConfig;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    try {
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      Enumeration<String> enumeration = filterConfig.getInitParameterNames();
      while (enumeration.hasMoreElements()) {
        String headerName = enumeration.nextElement();
        String headerValue = filterConfig.getInitParameter(headerName);
        httpServletResponse.setHeader(headerName, headerValue);
      }
      String serverIdKey = ApplicationConstant.ServerInfo.SERVER_ID;
      String geoipIdKey = ApplicationConstant.ServerInfo.GEOIP_ID;
      String geoipId = "";
      String serverId = "";
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      serverId = (String) httpServletRequest.getSession().getServletContext().getAttribute(serverIdKey);
      HttpSession httpSession = httpServletRequest.getSession(false);
      if (httpSession != null) {
        geoipId = (String) httpSession.getAttribute(geoipIdKey);
      }
      httpServletResponse.setHeader(serverIdKey, serverId);
      log.trace("header set...{}={}", serverIdKey, serverId);
      httpServletResponse.setHeader(geoipIdKey, geoipId);
      log.trace("header set...{}={}", geoipIdKey, geoipId);
      for (Entry<Object, Object> entry : WebUtil.getVersionProperties().entrySet()) {
        httpServletResponse.setHeader(entry.getKey().toString(), entry.getValue().toString());
        log.trace("header set...{}={}", entry.getKey().toString(), entry.getValue().toString());
      }
    } finally {
      chain.doFilter(request, response);
    }
  }

  @Override
  public void destroy() {
    this.filterConfig = null;
  }
}
