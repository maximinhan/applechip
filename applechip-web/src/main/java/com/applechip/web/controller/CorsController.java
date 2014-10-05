package com.applechip.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CorsController {
  private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
  private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
  private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
  private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
  private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
  private static final Integer DAY_IN_SECONDS = 24 * 60 * 60;

  @RequestMapping(method = RequestMethod.OPTIONS)
  public void handleOptionsRequest(@RequestHeader(value = ACCESS_CONTROL_REQUEST_METHOD, required = false) String requestMethods, @RequestHeader(value = ACCESS_CONTROL_REQUEST_HEADERS,
      required = false) String requestHeaders, HttpServletResponse response) {
    if (StringUtils.isNotBlank(requestMethods)) {
      response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, requestMethods);
    }

    if (StringUtils.isNotBlank(requestHeaders)) {
      response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);
    }
    response.setHeader(ACCESS_CONTROL_MAX_AGE, DAY_IN_SECONDS.toString());
  }
}
