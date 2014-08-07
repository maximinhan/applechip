package com.applechip.plugin.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PluginConstant {
  public static Map<String, String> HIBERNATE_DIALECT_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "org.hibernate.dialect.MySQL5InnoDBDialect");
      put("Oracle", "org.hibernate.dialect.Oracle10gDialect");
    }
  });
  public static Map<String, String> JDBC_DRIVER_CLASS_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5140274571534213417L;
    {
      put("MYSQL", "com.mysql.jdbc.Driver");
      put("Oracle", "oracle.jdbc.OracleDriver");
    }
  });
  public static Map<String, String> JDBC_VALIDATION_QUERY_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -2455854741489201645L;
    {
      put("MYSQL", "SELECT 1");
      put("Oracle", "SELECT 1 FROM DUAL");
    }
  });

  private PluginConstant() {}
}
