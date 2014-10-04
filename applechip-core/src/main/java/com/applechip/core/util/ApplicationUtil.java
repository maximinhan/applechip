package com.applechip.core.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	public String getMessage(String code) {
		return this.getMessage(code, WebUtil.getLocale());
	}

	public String getMessage(String code, Locale locale) {
		return this.getMessage(code, null, locale);
	}

	public String getMessage(String code, Object[] objects) {
		return this.getMessage(code, objects, WebUtil.getLocale());
	}

	public String getMessage(String code, Object[] objects, Locale locale) {
		String result = code;
		if (ArrayUtil.isEmpty(objects)) {
			result = messageSourceAccessor.getMessage(code, code, locale);
		}
		else {
			result = messageSourceAccessor.getMessage(code, objects, code, locale);
		}
		return result;
	}
}
