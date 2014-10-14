package com.applechip.core.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.Validator;
import org.springframework.web.context.ServletContextAware;

import com.applechip.core.exception.SystemException;
import com.applechip.core.util.IOUtil;

public abstract class AbstractController implements ServletContextAware {

  @Autowired(required = false)
  public Validator validator;

  @Autowired
  private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  private Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter;

  @Autowired
  private ByteArrayHttpMessageConverter byteArrayHttpMessageConverter;

  ObjectToStringHttpMessageConverter objectToStringHttpMessageConverter;

  protected void write(Object object, HttpServletResponse response, MediaType mediaType) {
    try {
      switch (mediaType.toString()) {
        case MediaType.APPLICATION_JSON_VALUE:
          jaxb2RootElementHttpMessageConverter.write(object, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
          break;
        case MediaType.APPLICATION_XML_VALUE:
        case MediaType.APPLICATION_XHTML_XML_VALUE:
        case MediaType.TEXT_XML_VALUE:
          jaxb2RootElementHttpMessageConverter.write(object, MediaType.TEXT_XML, new ServletServerHttpResponse(response));
          break;
        case MediaType.APPLICATION_OCTET_STREAM_VALUE:
          byteArrayHttpMessageConverter.write(IOUtil.toByteArray((InputStream) object), MediaType.APPLICATION_OCTET_STREAM, new ServletServerHttpResponse(response));
          break;
        default:
          objectToStringHttpMessageConverter.write(object, MediaType.TEXT_PLAIN, new ServletServerHttpResponse(response));
          break;
      }
    } catch (IOException e) {
      throw new SystemException("jaxb2RootElementHttpMessageConverter error", e);
    }
  }
}
