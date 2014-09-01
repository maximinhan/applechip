package com.applechip.core.abstact;

import java.text.MessageFormat;

import org.springframework.context.support.MessageSourceAccessor;

@SuppressWarnings("serial")
public abstract class AbstractRuntimeException extends RuntimeException {

  public Object[] arguments;

  public AbstractRuntimeException() {
    super();
  }

  public AbstractRuntimeException(Throwable cause, String message) {
    super(message, cause);
  }

  public AbstractRuntimeException(Throwable cause, String format, Object... arguments) {
    super(String.format(format, arguments), cause);
    this.arguments = arguments;
  }

  public String getMessage(MessageSourceAccessor messageSourceAccessor) {
    String pattern = messageSourceAccessor.getMessage("error." + getCode());
    return MessageFormat.format(pattern, arguments);
  }

  public abstract String getCode();

}
