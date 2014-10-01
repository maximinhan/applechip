package com.applechip.core.repository;

import java.util.List;

import com.applechip.core.entity.Code;
import com.applechip.core.entity.CodeDetail;

public interface ApplicationRepository {

	List<Code> getCodes(String codeCategory);

	List<CodeDetail> getCodeDetails(String codeCategory, String code);

}
