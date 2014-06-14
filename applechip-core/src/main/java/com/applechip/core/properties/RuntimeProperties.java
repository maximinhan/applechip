package com.applechip.core.properties;

import org.apache.commons.configuration.PropertiesConfiguration;


public class RuntimeProperties {

  private String[] serverAddress;

  public RuntimeProperties(PropertiesConfiguration runtimeProperties) {
    this.serverAddress = runtimeProperties.getStringArray("server.address");
  }

  public String[] getServerAddress() {
    return serverAddress;
  }
}
