package com.applechip.plugin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HibernateToolsConvention {
  private Boolean format = Boolean.TRUE;
  private Boolean comments = Boolean.TRUE;
  private String dialects = "org.hibernate.dialect.MySQL5Dialect";
  private String persistenceUnitName = "default";
  private String delimiter = ";";
  private String targetDirectory = "build/ddl";
  private String jdbcType = "MYSQL";
  private String jdbcUrl = "jdbc:mysql://localhost/applechip?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
  private String jdbcUsername = "root";
  private String jdbcPassword = "";
}
