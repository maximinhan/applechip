package com.applechip.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

import com.applechip.core.entity.Code;
import com.applechip.core.entity.CodeValue;
import com.applechip.core.repository.ApplicationRepository;
import com.applechip.core.util.ArrayUtil;
import com.applechip.core.util.StringUtil;
import com.applechip.core.util.WebUtil;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	private MessageSourceAccessor messageSourceAccessor;

	@Autowired
	public void setMessages(MessageSource messageSource) {
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	@Override
	public String getMessage(String code) {
		return this.getMessage(code, null);
	}

	@Override
	public String getMessage(String code, String codeCategory) {
		return this.getMessage(code, codeCategory, WebUtil.getLocale());
	}

	@Override
	public String getMessage(String code, String codeCategory, Locale locale) {
		return this.getMessage(code, codeCategory, locale, null);
	}

	@Override
	public String getMessage(String code, String codeCategory, Locale locale, Object[] objects) {
		if (StringUtil.isNotBlank(codeCategory)) {
			code = String.format("%s.%s", codeCategory, code);
		}
		String result = code;
		try {
			if (ArrayUtil.isEmpty(objects)) {
				result = messageSourceAccessor.getMessage(code, locale);
			}
			else {
				result = messageSourceAccessor.getMessage(code, objects, locale);
			}
		}
		catch (MissingResourceException e) {
		}
		return result;
	}

	@Override
//	 @Cacheable(key = "#codeCategory", value = { "CODE" })
	public List<CodeValue> getCodes(String codeCategory, Locale locale) {
		List<Code> codeList = applicationRepository.getCodeList(codeCategory);
		List<CodeValue> list = new ArrayList<CodeValue>();

		for (Code code : codeList) {
			list.add(new CodeValue(this.getMessage(code.getId().getCodeId(), codeCategory, locale), code.getId()
					.getCodeId()));
		}
		return list;
	}

}
