package com.applechip.core.exception;

import java.util.MissingResourceException;

import org.springframework.context.support.MessageSourceAccessor;

import com.applechip.core.util.ArrayUtil;

@SuppressWarnings("serial")
public abstract class AbstractRuntimeException extends RuntimeException {

	private static final String ERROR_FORMAT = "error.%s";

	private final Object[] objects;

	private final String code;

	public AbstractRuntimeException(String code) {
		this(code, null);
	}

	public AbstractRuntimeException(String code, Object[] objects) {
		this(code, objects, null);
	}

	public AbstractRuntimeException(String code, Object[] objects, String message) {
		this(code, objects, message, null);
	}

	public AbstractRuntimeException(String code, Object[] objects, String message, Throwable throwable) {
		super(message, throwable);
		this.code = code;
		this.objects = objects;
	}

	public String getDebugMessage() {
		return this.toString();
	}

	public String getMessage(MessageSourceAccessor messageSourceAccessor) {
		String result = String.format(ERROR_FORMAT, code);
		try {
			if (ArrayUtil.isEmpty(objects)) {
				result = messageSourceAccessor.getMessage(code);
			}
			else {
				result = messageSourceAccessor.getMessage(code, objects);
			}
		}
		catch (MissingResourceException e) {
			return super.getMessage();
		}
		return result;
	}
}
