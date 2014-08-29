package com.applechip.core.constant;

import java.nio.charset.Charset;

import org.apache.commons.codec.CharEncoding;

public class CoreConstant {
  public static final Charset CHARSET = Charset.forName(CharEncoding.UTF_8);
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  private static final String USER_HOME = System.getProperty("user.home");
  private static final String APPLECHIP_HOME = USER_HOME + FILE_SEPARATOR + ".bywook";
  public static final String CONFIG_PROPERTIES = "configProperties";
  public static final String CONFIG_PROPERTIES_PATH_DEV = "classpath:config.properties";
  public static final String CONFIG_PROPERTIES_PATH_PRO = "file:" + APPLECHIP_HOME + FILE_SEPARATOR + "config.properties";

  public static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE_UNIT";
  public static final String ENVIRONMENT = "environment";
  public static final String ENVIRONMENT_DEVELOPMENT = "development";

  protected int hashcode = System.identityHashCode(getClass());


}
