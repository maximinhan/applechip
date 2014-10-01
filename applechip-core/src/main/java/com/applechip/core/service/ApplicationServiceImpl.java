package com.applechip.core.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.applechip.core.entity.Code;
import com.applechip.core.entity.CodeDetail;
import com.applechip.core.entity.CodeValue;
import com.applechip.core.repository.ApplicationRepository;
import com.applechip.core.util.ApplicationUtil;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicationUtil applicationUtil;

	@Override
	@Cacheable(value = "getCodes", key = "#codeCategory")
	public List<CodeValue> getCodes(String codeCategory) {
		List<Code> codes = applicationRepository.getCodes(codeCategory);
		List<CodeValue> list = new ArrayList<CodeValue>();

		for (Code code : codes) {
			list.add(new CodeValue(code.getId().getId(), applicationUtil.getMessage(code.getId().getCodeId(),
					codeCategory)));
		}
		return list;
	}

	public List<CodeValue> getCodeDetails(String codeCategory, String code) {
		List<CodeDetail> codeDetails = applicationRepository.getCodeDetails(codeCategory, code);
		List<CodeValue> list = new ArrayList<CodeValue>();

		for (CodeDetail codeDetail : codeDetails) {
			list.add(new CodeValue(codeDetail.getId().getId(), applicationUtil.getMessage(codeDetail.getId()
					.getCodeId(), codeCategory)));
		}
		return list;
	}

}
