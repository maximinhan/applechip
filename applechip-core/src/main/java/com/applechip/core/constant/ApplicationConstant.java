package com.applechip.core.constant;

public class ApplicationConstant extends SystemConstant {
  private ApplicationConstant() {}

  public static final String APPLECHIP_HOME = String.format("%s%s.applechip", USER_HOME, FILE_SEPARATOR);
  public static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE_UNIT";

  public static final class PropertiesPath {
    public static final String CONFIG_PROPERTIES = String.format("classpath:META-INF%sconfig.properties", FILE_SEPARATOR);
    public static final String CONFIG_PROPERTIES_PRODUCTION = String.format("file:%s%sconfig.properties", APPLECHIP_HOME, FILE_SEPARATOR);
    public static final String APPLICATION_PROPERTIES = String.format("classpath:META-INF%sproperties%sapplication.properties", FILE_SEPARATOR, FILE_SEPARATOR);
  }

  public static final class MessageResource {
    public static final String APPLICATION = String.format("META-INF/message.%s", "ApplicationResource");
  }

  public static final class ServerInfo {
    public static final String VERSION_TXT = "classpath*:version.txt";
    public static final String SERVER_ID = "serverId";
    public static final String GEOIP_ID = "geoipId";
    public static final String GEOIP_LOCATION = "geoipLocation";
    public static final String GEOIP_GROUP_MAP = "geoipGroupMap";
  }
}
