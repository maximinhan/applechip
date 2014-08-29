package com.applechip.core.constant;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.applechip.core.util.ReflectUtil;

public class DatabaseConstant {

  public static Map<String, String> HIBERNATE_DIALECT_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "org.hibernate.dialect.MySQL5InnoDBDialect");
      put("MYSQL_REPLICATION", "org.hibernate.dialect.MySQL5InnoDBDialect");
      put("ORACLE", "org.hibernate.dialect.Oracle10gDialect");
      put("SQLSERVER", "org.hibernate.dialect.SQLServer2008Dialect");
    }
  });
  public static Map<String, String> JDBC_DRIVER_CLASS_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "com.mysql.jdbc.Driver");
      put("MYSQL_REPLICATION", "com.mysql.jdbc.ReplicationDriver");
      put("ORACLE", "oracle.jdbc.OracleDriver");
      put("SQLSERVER", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
  });
  public static Map<String, String> JDBC_VALIDATION_QUERY_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "SELECT 1");
      put("MYSQL_REPLICATION", "/* ping */ SELECT 1");
      put("ORACLE", "SELECT 1 FROM DUAL");
      put("SQLSERVER", "SELECT 1");
    }
  });

  public static Set<String> DATA_SOURCE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
    private static final long serialVersionUID = -5954861215952396300L;
    {
      for (Field field : BasicDataSourceFactory.class.getDeclaredFields()) {
        Object object = ReflectUtil.readField(field, String.class);
        if (object != null) {
          add(object.toString());
        }
      }
    }
  });
  public static Set<String> HIBERNATE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
    private static final long serialVersionUID = -5954861215952396300L;
    {
      Field[] cfg = org.hibernate.cfg.AvailableSettings.class.getDeclaredFields();
      Field[] jpa = org.hibernate.jpa.AvailableSettings.class.getDeclaredFields();
      Field[] fields = Arrays.copyOf(cfg, cfg.length + jpa.length);
      System.arraycopy(jpa, 0, fields, cfg.length, jpa.length);
      for (Field field : fields) {
        Object object = ReflectUtil.readField(field, String.class);
        if (object != null) {
          add(object.toString());
        }
      }
    }
  });
}
