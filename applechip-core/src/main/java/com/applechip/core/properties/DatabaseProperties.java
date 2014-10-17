package com.applechip.core.properties;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import lombok.Getter;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;

import com.applechip.core.object.AbstractObject;
import com.applechip.core.util.FieldUtil;
import com.applechip.core.util.SecurityUtil;

@Getter
public class DatabaseProperties extends AbstractObject {
  private static final long serialVersionUID = -7775672004964113819L;

  private static Set<String> DATA_SOURCE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
    private static final long serialVersionUID = 7526702150204237983L;
    {
      addAll(FieldUtil.readFieldList(BasicDataSourceFactory.class, String.class));
    }
  });

  private static Set<String> HIBERNATE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
    private static final long serialVersionUID = 533898394580130849L;
    {
      addAll(FieldUtil.readFieldList(org.hibernate.cfg.AvailableSettings.class, String.class));
      addAll(FieldUtil.readFieldList(org.hibernate.jpa.AvailableSettings.class, String.class));
      addAll(FieldUtil.readFieldList(org.hibernate.envers.configuration.EnversSettings.class, String.class));
    }
  });

  private Properties hibernateProperties;

  private Properties dataSourceProperties;

  @Value("${database.packagesToScan}")
  private String[] packagesToScan;

  @Value("${database.type}")
  private String type;

  public DatabaseProperties(Properties properties) {
    this.setDataSourceProperties(properties);
    this.setHibernateProperties(properties);
  }

  private void setDataSourceProperties(Properties properties) {
    Properties result = new Properties();
    for (String string : DATA_SOURCE_PROPERTIES_SET) {
      if (properties.containsKey(string)) {
        String value = properties.getProperty(string);
        result.put(string, SecurityUtil.forceDecrypt(value));
      }
    }
    this.dataSourceProperties = result;
  }

  private void setHibernateProperties(Properties properties) {
    Properties result = new Properties();
    for (String string : HIBERNATE_PROPERTIES_SET) {
      if (properties.containsKey(string)) {
        String value = properties.getProperty(string);
        result.put(string, SecurityUtil.forceDecrypt(value));
      }
    }
    this.hibernateProperties = result;
  }
}
