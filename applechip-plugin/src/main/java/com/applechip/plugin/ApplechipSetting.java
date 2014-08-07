package com.applechip.plugin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApplechipSetting {
  private String jdbcType = "MYSQL";
  private String jdbcUrl =
      "jdbc:mysql://localhost/applechip?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
  private String jdbcUsername = "root";
  private String jdbcPassword = "";
}
