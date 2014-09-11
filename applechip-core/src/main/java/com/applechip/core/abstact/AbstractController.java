package com.applechip.core.abstact;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.validation.Validator;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.applechip.core.exception.SystemException;

@Getter
@Setter
public abstract class AbstractController implements ServletContextAware {

	private ServletContext servletContext;

	@Autowired(required = false)
	public Validator validator;

	@Autowired
	private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private Jaxb2RootElementHttpMessageConverter jaxb2RootElementHttpMessageConverter;

	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	public void setMessageSourceAccessor(MessageSource messageSource) {
		messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	protected String getText(String code, Locale locale) {
		return messageSourceAccessor.getMessage(code, locale);
	}

	protected String getText(String code, Object[] args, Locale locale) {
		return messageSourceAccessor.getMessage(code, args, locale);
	}

	protected void writeJson(Object obj, HttpServletResponse response) {
		try {
			mappingJackson2HttpMessageConverter.write(obj, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(
					response));
		}
		catch (IOException e) {
			throw new SystemException(e, "mappingJackson2HttpMessageConverter error");
		}
	}

	protected void writeXml(Object obj, HttpServletResponse response) {
		try {
			jaxb2RootElementHttpMessageConverter
					.write(obj, MediaType.TEXT_XML, new ServletServerHttpResponse(response));
		}
		catch (IOException e) {
			throw new SystemException(e, "jaxb2RootElementHttpMessageConverter error");
		}
	}
}
