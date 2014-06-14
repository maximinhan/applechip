package com.applechip.core.abstact;

import java.text.MessageFormat;

import org.springframework.context.support.MessageSourceAccessor;

@SuppressWarnings("serial")
public abstract class AbstractRuntimeException extends RuntimeException {

  public Object[] objects;

  public AbstractRuntimeException() {
    super();
  }

  public AbstractRuntimeException(Throwable throwable) {
    super(throwable);
  }

  public AbstractRuntimeException(String message) {
    super(message);
  }

  public AbstractRuntimeException(String message, Throwable throwable) {
    super(message, throwable);
  }

  public AbstractRuntimeException(String format, Object... objects) {
    super(String.format(format, objects));
    this.objects = objects;
  }

  public abstract String getExceptionCode();

  public String getExceptionDebugMessage() {
    return this.toString();
  }

  public String getExceptionMessage(MessageSourceAccessor messageSourceAccessor) {
    String errorMessageFormat = messageSourceAccessor.getMessage("error." + getExceptionCode());
    return MessageFormat.format(errorMessageFormat, objects);
  }
}
