package com.applechip.core.constant;

public class ReturnCodeConstant {
  public static final String OK = "100";

  public static final String ERROR_SYSTEM_EXCEPTION = "999";
  public static final String ERROR_BINDING = "900";
  public static final String ERROR_DATA_SAVE_FAIL = "990";
  public static final String ERROR_REQUIRED_DATA_NOT_FOUND = "992";
  public static final String ERROR_HTTP_CLIENT_RESPONSE = "998";
  public static final String ERROR_CONCURRENCY_CONTROL_FAIL = "808";
  public static final String ERROR_PARAMETER_EMPTY = "901";
  public static final String ERROR_PARAMETER_INVALID = "902";
  public static final String ERROR_SESSION_TIMEOUT = "910";
  public static final String ERROR_ENTITY_NOT_EXIST = "993";

  public static final class LoginFailCodes {
    public static final String BAD_CREDENTIAL = "1";
    public static final String CREDENTIAL_EXPIRED = "2";
    public static final String NOT_AUTHORIEZED = "3";
  }
}
