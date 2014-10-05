package com.applechip.core.util;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil extends DateUtils {

  public static final String DATE_PATTERN = "";
  public static final String DATE_TIMESTAMP_PATTERN = "";
  public static final String DATE_FULL_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  public static Date getDate() {
    return new Date();
  }
}
