package com.applechip.core.constant;

import java.nio.charset.Charset;

import org.apache.commons.codec.CharEncoding;

public class CoreConstant {
  public static final Charset CHARSET = Charset.forName(CharEncoding.UTF_8);
  public static final String CHARSET_TO_STRING = CHARSET.toString();
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private static final String USER_HOME = System.getProperty("user.home");
  private static final String APPLECHIP_HOME = String.format("%s%s.applechip", USER_HOME,
      FILE_SEPARATOR);
  public static final String STORAGE_PATH = String.format("%s%sstorage", APPLECHIP_HOME,
      FILE_SEPARATOR);
  private static final String CONFIG_PROPERTIES = "config.properties";
  public static final String CONFIG_PROPERTIES_DEVELOPMENT = String.format("classpath:%s",
      CONFIG_PROPERTIES);
  public static final String CONFIG_PROPERTIES_PRODUCTION = String.format("file:%s%s%s",
      APPLECHIP_HOME, FILE_SEPARATOR, CONFIG_PROPERTIES);

  public static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE_UNIT";

}
