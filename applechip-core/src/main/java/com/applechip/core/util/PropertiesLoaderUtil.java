package com.applechip.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.applechip.core.exception.SystemException;

public class PropertiesLoaderUtil extends PropertiesLoaderUtils {

  public static Properties getProperties(String location) {
    Properties properties = null;
    try {
      properties = loadProperties(getResource(location));
    } catch (IOException e) {
      throw new SystemException(String.format("getProperties fail... location: %s, message: %s", location, e.getMessage()), e);
    }
    return properties;
  }

  public static Resource getResource(String location) {
    return new PathMatchingResourcePatternResolver().getResource(location);
  }

  public static Resource[] getResources(String... locations) {
    List<Resource> list = new ArrayList<Resource>();
    for (String location : locations) {
      list.add(getResource(location));
    }
    return list.toArray(new Resource[list.size()]);
  }

  public static Properties stringToProperties(String string, char arrayPattern, char valuePattern) {
    Properties properties = new Properties();
    for (String s : StringUtil.split(string, arrayPattern)) {
      String[] values = StringUtil.split(s, valuePattern);
      properties.put(values[0], values[1]);
    }
    return properties;
  }

}
