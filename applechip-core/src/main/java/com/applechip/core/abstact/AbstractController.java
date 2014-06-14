package com.applechip.core.abstact;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Validator;
import org.springframework.web.context.ServletContextAware;

import com.applechip.core.entity.User;
import com.applechip.core.exception.SystemException;

public abstract class AbstractController implements ServletContextAware {

  protected final transient Log log = LogFactory.getLog(getClass());

  private ServletContext servletContext;

  @Autowired(required = false)
  public Validator validator;

//  @Autowired
//  protected LookupManager lookupManager;

  @Autowired
  private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

  @Autowired
  private Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter;

  private MessageSourceAccessor messageSourceAccessor;

  @Autowired
  public void setMessages(MessageSource messageSource) {
    messageSourceAccessor = new MessageSourceAccessor(messageSource);
  }

  public String getText(String msgKey, Locale locale) {
    return messageSourceAccessor.getMessage(msgKey, locale);
  }

  public String getText(String msgKey, Object[] args, Locale locale) {
    return messageSourceAccessor.getMessage(msgKey, args, locale);
  }

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public ServletContext getServletContext() {
    return servletContext;
  }

  public String getLanguage() {
    return LocaleContextHolder.getLocale().getLanguage();
  }

  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

  protected void writeJson(Object obj, HttpServletResponse response) {
    MediaType mediaType = MediaType.APPLICATION_JSON;
    writeJson(obj, mediaType, response);
  }

  protected void writeJson(Object obj, MediaType mediaType, HttpServletResponse response) {
    try {
      if (!mappingJackson2HttpMessageConverter.canWrite(obj.getClass(), mediaType)) {
        throw new SystemException("json create error");
      }
      mappingJackson2HttpMessageConverter.write(obj, mediaType, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new RuntimeException("mappingJackson2HttpMessageConverter error");
    }
  }

  protected void writeXml(Object obj, HttpServletResponse response) {
    MediaType mediaType = MediaType.TEXT_XML;
    writeXml(obj, mediaType, response);
  }

  protected void writeXml(Object obj, MediaType mediaType, HttpServletResponse response) {
    try {
      if (!jaxb2RootElementHttpMessageConverter.canWrite(obj.getClass(), mediaType)) {
        throw new SystemException("xml create error");
      }
      jaxb2RootElementHttpMessageConverter.write(obj, mediaType, new ServletServerHttpResponse(response));
    } catch (IOException e) {
      throw new RuntimeException("jaxb2RootElementHttpMessageConverter error");
    }
  }

  public User getCurrentUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    User currentUser = null;
    if (ctx.getAuthentication() != null) {
      Authentication auth = ctx.getAuthentication();
      AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
      boolean signupUser = resolver.isAnonymous(auth);
      if (!signupUser) {
        if (auth.getPrincipal() instanceof UserDetails) {
          currentUser = (User) auth.getPrincipal();
        } else if (auth.getDetails() instanceof UserDetails) {
          currentUser = (User) auth.getDetails();
        } else {
          throw new AccessDeniedException("User not properly authenticated.");
        }
      }
    }
    return currentUser;
  }
}
