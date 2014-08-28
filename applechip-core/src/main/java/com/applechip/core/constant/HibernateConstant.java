package com.applechip.core.constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HibernateConstant {

  public static Map<String, String> HIBERNATE_DIALECT_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "org.hibernate.dialect.MySQL5InnoDBDialect");
      put("MYSQL_REPLICATION", "org.hibernate.dialect.MySQL5InnoDBDialect");
      put("ORACLE", "org.hibernate.dialect.Oracle10gDialect");
    }
  });
  public static Map<String, String> JDBC_DRIVER_CLASS_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "com.mysql.jdbc.Driver");
      put("MYSQL_REPLICATION", "com.mysql.jdbc.ReplicationDriver");
      put("ORACLE", "oracle.jdbc.OracleDriver");
    }
  });
  public static Map<String, String> JDBC_VALIDATION_QUERY_MAP = Collections.unmodifiableMap(new HashMap<String, String>() {
    private static final long serialVersionUID = -5418896212085484154L;
    {
      put("MYSQL", "SELECT 1");
      put("MYSQL_REPLICATION", "/* ping */ SELECT 1");
      put("ORACLE", "SELECT 1 FROM DUAL");
    }
  });

  public static List<String> JDBC_PROPERTIES_LIST = Collections.unmodifiableList(new ArrayList<String>() {
    private static final long serialVersionUID = -5954861215952396300L;
    {
      add("defaultAutoCommit");
      add("defaultReadOnly");
      add("defaultTransactionIsolation");
      add("defaultCatalog");
      add("driverClassName");
      add("maxActive");
      add("maxIdle");
      add("minIdle");
      add("initialSize");
      add("maxWait");
      add("testOnBorrow");
      add("testOnReturn");
      add("timeBetweenEvictionRunsMillis");
      add("numTestsPerEvictionRun");
      add("minEvictableIdleTimeMillis");
      add("testWhileIdle");
      add("password");
      add("url");
      add("username");
      add("validationQuery");
      add("validationQueryTimeout");
      add("initConnectionSqls");
      add("accessToUnderlyingConnectionAllowed");
      add("removeAbandoned");
      add("removeAbandonedTimeout");
      add("logAbandoned");
      add("poolPreparedStatements");
      add("maxOpenPreparedStatements");
      add("connectionProperties");
    }
  });
}
