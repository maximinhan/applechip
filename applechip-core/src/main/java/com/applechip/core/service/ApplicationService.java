package com.applechip.core.service;

import java.util.List;

import com.applechip.core.entity.CodeValue;

public interface ApplicationService {

	List<CodeValue> getCodes(String codeCategory);

	List<CodeValue> getCodeDetails(String codeCategory, String code);

}
