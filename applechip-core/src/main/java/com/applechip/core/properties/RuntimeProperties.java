package com.applechip.core.properties;

import lombok.Getter;

import org.apache.commons.configuration.PropertiesConfiguration;


@Getter
public class RuntimeProperties {

  private String[] serverAddress;

  public static RuntimeProperties getInstance(PropertiesConfiguration properties) {
    return new RuntimeProperties(properties);
  }

  private RuntimeProperties(PropertiesConfiguration runtimeProperties) {
    this.serverAddress = runtimeProperties.getStringArray("server.address");
  }
}
