package com.applechip.core.exception;


public class BusinessException extends AbstractRuntimeException {
	private static final long serialVersionUID = 6953304855598597942L;

	public BusinessException(String code) {
		this(code, null, null);
	}

	public BusinessException(String code, Object[] objects) {
		this(code, objects, null);
	}

	public BusinessException(String code, String message) {
		this(code, null, message);
	}

	public BusinessException(String code, Object[] objects, String message) {
		super(code, objects, message);
	}
}
