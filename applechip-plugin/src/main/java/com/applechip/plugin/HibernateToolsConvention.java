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
}
