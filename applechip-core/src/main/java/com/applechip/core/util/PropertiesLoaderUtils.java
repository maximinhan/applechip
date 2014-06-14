package com.applechip.core.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.Resource;

import com.applechip.core.exception.SystemException;

public class PropertiesLoaderUtils {

  public static Properties loadProperties(Resource resource) {
    Properties properties = null;
    try {
      properties = org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties(resource);
    } catch (IOException e) {
      throw new SystemException(e);
    }
    return properties;
  }

}
