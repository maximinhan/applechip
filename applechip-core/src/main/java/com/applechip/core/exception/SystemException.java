package com.applechip.core.exception;

import com.applechip.core.abstact.AbstractRuntimeException;
import com.applechip.core.constant.ReturnCodeConstant;


public class SystemException extends AbstractRuntimeException {

  private static final long serialVersionUID = 6179610209426537025L;

  private String code;

  public SystemException() {
    super();
    this.code = ReturnCodeConstant.ERROR_SYSTEM_EXCEPTION;
  }

  public SystemException(String code) {
    this.code = code;
  }

  public SystemException(Throwable cause, String message) {
    super(cause, message);
  }

  public SystemException(Throwable cause, String format, Object... arguments) {
    super(cause, format, arguments);
  }

  @Override
  public String getCode() {
    return code;
  }
}
