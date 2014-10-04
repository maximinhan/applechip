package com.applechip.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.Validator;
import org.springframework.web.context.ServletContextAware;

import com.applechip.core.exception.SystemException;

public abstract class AbstractController implements ServletContextAware {

  @Autowired(required = false)
  public Validator validator;

  @Autowired
  private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  private Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter;

  protected void writeJson(Object object, HttpServletResponse response) {
    try {
      mappingJackson2HttpMessageConverter.write(object, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new SystemException("mappingJackson2HttpMessageConverter error", e);
    }
  }

  protected void writeXml(Object object, HttpServletResponse response) {
    try {
      jaxb2RootElementHttpMessageConverter.write(object, MediaType.TEXT_XML, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new SystemException("jaxb2RootElementHttpMessageConverter error", e);
    }
  }
}
