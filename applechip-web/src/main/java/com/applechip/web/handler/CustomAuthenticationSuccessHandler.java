package com.applechip.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
    if ("application/json".equals(request.getHeader("Content-Type"))) {
      response.getWriter().print("{\"responseCode\":\"SUCCESS\"}");
      response.getWriter().flush();
    } else {
      super.onAuthenticationSuccess(request, response, auth);
    }
  }
}
