package com.applechip.core.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.applechip.core.exception.SystemException;

public class PropertiesLoaderUtil {

  public static Properties loadProperties(Resource resource) {
    Properties properties = null;
    try {
      properties =
          org.springframework.core.io.support.PropertiesLoaderUtils.loadProperties(resource);
    } catch (IOException e) {
      throw new SystemException(e, "loadProperties fail... filename: %s, message: %s",
          resource.getFilename(), e.getMessage());
    }
    return properties;
  }

  public static Resource[] getResources(String... resources) {
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =
        new PathMatchingResourcePatternResolver();
    List<Resource> list = new ArrayList<Resource>();
    for (String resource : resources) {
      list.add(pathMatchingResourcePatternResolver.getResource(resource));
    }
    return list.toArray(new Resource[list.size()]);
  }

}
