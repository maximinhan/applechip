package com.applechip.core.exception;

import com.applechip.core.constant.ReturnConstant;

public class EntityNotFoundException extends BusinessException {

	private static final long serialVersionUID = 3100698543588914032L;

	public EntityNotFoundException(String id, String message) {
		super(ReturnConstant.ERROR_ENTITY_NOT_FOUND, new Object[] { id }, message);
	}
}
