package com.applechip.core.properties;

import java.util.Properties;

import lombok.Getter;

import org.apache.commons.configuration.PropertiesConfiguration;


@Getter
public class RuntimeProperties extends CoreProperties {

  private String[] serverAddress;

  private PropertiesConfiguration propertiesConfiguration;

  public RuntimeProperties(Properties properties, PropertiesConfiguration propertiesConfiguration) {
    super(properties);
    this.setProperties(propertiesConfiguration);
  }

  private void setProperties(PropertiesConfiguration propertiesConfiguration) {
    this.serverAddress = propertiesConfiguration.getStringArray("server.address");
  }
}
