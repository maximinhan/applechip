package com.applechip.core.util;

import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebUtil extends WebUtils {
	public static String toJsonString(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "{}";
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return jsonString;
	}

	public static String toXmlString(Object object) {
		StringWriter writer = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			m.marshal(object, writer);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return writer.toString();
	}

	public static RequestAttributes currentRequestAttributes() {
		return RequestContextHolder.currentRequestAttributes();
	}

	public static HttpServletRequest getCurrentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder
				.getRequestAttributes();
		Assert.state(requestAttributes != null,
				"Could not find current request via RequestContextHolder");
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes)
				.getRequest();
		Assert.state(httpServletRequest != null,
				"Could not find current HttpServletRequest");
		return httpServletRequest;
	}

	public static String getLanguage() {
		return getLocale().getLanguage();
	}

	public static Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}
