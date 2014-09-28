package com.applechip.core.exception;

import com.applechip.core.abstact.AbstractRuntimeException;
import com.applechip.core.constant.ReturnCodeConstant;

public class SystemException extends AbstractRuntimeException {

	private static final long serialVersionUID = 6179610209426537025L;

	public SystemException() {
		super(ReturnCodeConstant.ERROR_SYSTEM_EXCEPTION);
	}

	public SystemException(Throwable throwable) {
		this(null, throwable);
	}

	public SystemException(String message) {
		this(message, null);
	}

	public SystemException(String message, Throwable throwable) {
		super(ReturnCodeConstant.ERROR_SYSTEM_EXCEPTION, null, message, throwable);
	}
}
