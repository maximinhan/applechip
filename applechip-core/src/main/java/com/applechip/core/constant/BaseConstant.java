package com.applechip.core.constant;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.CharEncoding;

public class BaseConstant {
  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  public static final String USER_HOME = System.getProperty("user.home") + FILE_SEPARATOR;
  public static final String APPLECHIP_HOME = USER_HOME + ".bywook" + FILE_SEPARATOR;
  public static final String CONFIG_PROPERTIES = "configProperties";
  public static final String CONFIG_PROPERTIES_PATH_DEV = "classpath:config.properties";
  public static final String CONFIG_PROPERTIES_PATH_PRO = "file:" + APPLECHIP_HOME + "config.properties";

  public static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE_UNIT";
  public static final String ENVIRONMENT = "environment";
  public static final String ENVIRONMENT_DEVELOPMENT = "development";

  protected int hashcode = System.identityHashCode(getClass());

  public static final Charset CHARSET = Charset.forName(CharEncoding.UTF_8);

  public static final String TYPE_DONE = "done";
  public static final String TYPE_CHAT = "chat";
  public static final String TYPE_HELP = "help";

  public static final List<String> bankNames = Collections.unmodifiableList(new ArrayList<String>() {
    private static final long serialVersionUID = 8692172397880388698L;
    {
      add("Insurance");
      add("Treatment");
      add("Loans");
      add("Attorney");
      add("Mortgage");
      add("Hosting");
      add("Rehab");
      add("Classes");
      add("Transfer");
      add("Recovery");
      add("Software");
      add("Claim");
      add("Trading");
      add("Lawyer");
      add("Donate");
      add("Credit");
      add("Degree");
    }
  });
}
