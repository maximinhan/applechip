package com.applechip.core.util;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.web.util.CustomObjectMapper;

public class WebUtil extends WebUtils {
  public static String toJsonString(Object object) {
    CustomObjectMapper objectMapper = new CustomObjectMapper();
    String jsonString = "{}";
    try {
      jsonString = objectMapper.writeValueAsString(object);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    return jsonString;
  }

  public static <T> T toJsonObject(String body, Class<T> object) {
    CustomObjectMapper objectMapper = new CustomObjectMapper();
    T t = null;
    try {
      t = objectMapper.readValue(body, object);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    return t;
  }

  public static String toXmlString(Object object) {
    StringWriter stringWriter = new StringWriter();
    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = jaxbContext.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
      marshaller.marshal(object, stringWriter);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return stringWriter.toString();
  }

  public static RequestAttributes currentRequestAttributes() {
    return RequestContextHolder.currentRequestAttributes();
  }

  public static HttpServletRequest getCurrentRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
    Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
    Assert.state(httpServletRequest != null, "Could not find current HttpServletRequest");
    return httpServletRequest;
  }

  public static String getLanguage() {
    return getLocale().getLanguage();
  }

  public static Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

  public static String getRandomNumberString(int size) {
    String format = "";
    for (int i = 0; i < size; i++) {
      format = format.concat("0");
    }
    DecimalFormat df = new DecimalFormat(format);
    Random generator = new Random();
    return df.format(generator.nextDouble() * Math.pow(10, size));
  }

  public static Properties getVersionProperties() {
    return PropertiesLoaderUtil.getProperties(ApplicationConstant.ServerInfo.VERSION_TXT);
  }
}
