package com.applechip.core.abstact;

import java.text.MessageFormat;

import org.springframework.context.support.MessageSourceAccessor;

@SuppressWarnings("serial")
public abstract class AbstractRuntimeException extends RuntimeException {

  public Object[] messageArgs;

  public AbstractRuntimeException() {
    super();
  }

  public AbstractRuntimeException(Throwable cause) {
    super(cause);
  }

  public AbstractRuntimeException(String message) {
    super(message);
  }

  public AbstractRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public AbstractRuntimeException(String format, Object... args) {
    super(String.format(format, args));
    this.messageArgs = args;
  }

  public abstract String getExceptionCode();

  public String getExceptionDebugMessage() {
    return this.toString();
  }

  public String getExceptionMessage(MessageSourceAccessor messageSourceAccessor) {
    String errorMessageFormat = messageSourceAccessor.getMessage("error." + getExceptionCode());
    return MessageFormat.format(errorMessageFormat, messageArgs);
  }
}
