package com.applechip.core.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebUtil {
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
}
