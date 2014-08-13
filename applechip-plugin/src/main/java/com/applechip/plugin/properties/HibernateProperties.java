package com.applechip.plugin.properties;

import java.util.Properties;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.cfg.Environment;

import com.applechip.plugin.ApplechipSetting;
import com.applechip.plugin.constant.PluginConstant;

@Getter
@Setter
public class HibernateProperties {
  private String jdbcType;

  private Properties properties = new Properties();

  public static Properties getProperties(ApplechipSetting applechipSetting) {
    return new HibernateProperties(applechipSetting).getProperties();
  }

  private HibernateProperties(ApplechipSetting applechipSetting) {
    this.jdbcType = applechipSetting.getJdbcType();
    properties.put(Environment.DRIVER, PluginConstant.JDBC_DRIVER_CLASS_MAP.get(this.jdbcType));
    properties.put(Environment.DIALECT, PluginConstant.HIBERNATE_DIALECT_MAP.get(this.jdbcType));
    properties.put(Environment.URL, applechipSetting.getJdbcUrl());
    properties.put(Environment.USER, applechipSetting.getJdbcUsername());
  }
}
