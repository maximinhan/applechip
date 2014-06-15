package com.applechip.core.constant;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.CharEncoding;

public class BaseConstant {
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  public static final String USER_HOME = System.getProperty("user.home") + FILE_SEPARATOR;
  public static final String APPLECHIP_HOME = USER_HOME + ".bywook" + FILE_SEPARATOR;
  public static final String CONFIG_PROPERTIES = "configProperties";
  public static final String CONFIG_PROPERTIES_PATH_DEV = "classpath:config.properties";
  public static final String CONFIG_PROPERTIES_PATH_PRO = "file:" + APPLECHIP_HOME + "config.properties";

  public static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE_UNIT";
  public static final String ENVIRONMENT = "environment";
  public static final String ENVIRONMENT_DEVELOPMENT = "development";
  
  public static final String HIBERNATE_DIALECT_MYSQL = "org.hibernate.dialect.MySQL5InnoDBDialect";
  public static final String JDBC_DRIVERCLASSNAME_MYSQL = "com.mysql.jdbc.Driver";
  public static final String JDBC_VALIDATIONQUERY_MYSQL = "SELECT 1";
  public static final String JDBC_URL_MYSQL = "jdbc:mysql://%s";
  
  public static final String HIBERNATE_DIALECT_MYSQL_REPL = HIBERNATE_DIALECT_MYSQL;
  public static final String JDBC_DRIVERCLASSNAME_MYSQL_REPL = "com.mysql.jdbc.ReplicationDriver";
  public static final String JDBC_VALIDATIONQUERY_MYSQL_REPL = "/* ping */ SELECT 1";
  public static final String JDBC_URL_MYSQL_REPL = "jdbc:mysql:replication://%s";
  
  public static final String HIBERNATE_DIALECT_SQL_SERVER = "org.hibernate.dialect.SQLServerDialect";
  public static final String JDBC_DRIVERCLASSNAME_SQL_SERVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
  public static final String JDBC_VALIDATIONQUERY_SQL_SERVER = "SELECT 1";
  public static final String JDBC_URL_SQL_SERVER = "jdbc:sqlserver://%s";
  
  public static final String HIBERNATE_DIALECT_ORACLE = "org.hibernate.dialect.Oracle10gDialect";
  public static final String JDBC_DRIVERCLASSNAME_ORACLE = "oracle.jdbc.OracleDriver";
  public static final String JDBC_VALIDATIONQUERY_ORACLE = "SELECT 1 FROM DUAL";
  public static final String JDBC_URL_ORACLE = "jdbc:oracle:thin:@%s";
  
  

  protected int hashcode = System.identityHashCode(getClass());

  public static final Charset CHARSET = Charset.forName(CharEncoding.UTF_8);

  public static final String TYPE_DONE = "done";
  public static final String TYPE_CHAT = "chat";
  public static final String TYPE_HELP = "help";

  public static final List<String> bankNames = Collections.unmodifiableList(new ArrayList<String>() {
    private static final long serialVersionUID = 8692172397880388698L;
    {
      add("Insurance");
      add("Treatment");
      add("Loans");
      add("Attorney");
      add("Mortgage");
      add("Hosting");
      add("Rehab");
      add("Classes");
      add("Transfer");
      add("Recovery");
      add("Software");
      add("Claim");
      add("Trading");
      add("Lawyer");
      add("Donate");
      add("Credit");
      add("Degree");
    }
  });
}
