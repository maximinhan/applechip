package com.applechip.core.service;

import java.util.List;
import java.util.Locale;

import com.applechip.core.entity.CodeValue;

public interface ApplicationService {

	String getMessage(String code);

	String getMessage(String code, String codeCategory);

	String getMessage(String code, String codeCategory, Locale locale);

	String getMessage(String code, String codeCategory, Locale locale, Object[] objects);

	List<CodeValue> getCodes(String codeCategory, Locale locale);

}
