package com.applechip.core.exception;

import com.applechip.core.abstact.AbstractRuntimeException;
import com.applechip.core.constant.ReturnCodeConstant;


public class SystemException extends AbstractRuntimeException {

  private static final long serialVersionUID = 6179610209426537025L;

  public SystemException(final String message) {
    super(message);
  }

  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }

  public SystemException() {
    super();
  }

  public SystemException(String format, Object... args) {
    super(format, args);
  }

  public SystemException(Throwable cause) {
    super(cause);
  }

  @Override
  public String getExceptionCode() {
    return ReturnCodeConstant.ERROR_SYSTEM_EXCEPTION;
  }
}