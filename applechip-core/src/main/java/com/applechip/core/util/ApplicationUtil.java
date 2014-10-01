package com.applechip.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	public String getMessage(String codeCategory) {
		return this.getMessage(codeCategory, null);
	}

	public String getMessage(String codeCategory, String code) {
		return this.getMessage(codeCategory, code, null);
	}

	public String getMessage(String codeCategory, String code, String codeDetail) {
		return this.getMessage(codeCategory, code, codeDetail, null);
	}

	public String getMessage(String codeCategory, String code, String codeDetail, Object[] objects) {
		StringBuilder sb = new StringBuilder(codeCategory);
		if (StringUtil.isNotBlank(code)) {
			sb.append(String.format(".%s", code));
			if (StringUtil.isNotBlank(codeDetail)) {
				sb.append(String.format(".%s", codeDetail));
			}
		}
		String result = codeCategory;
		if (ArrayUtil.isEmpty(objects)) {
			result = messageSourceAccessor.getMessage(sb.toString(), sb.toString());
		}
		else {
			result = messageSourceAccessor.getMessage(sb.toString(), objects, sb.toString());
		}
		return result;
	}

}
