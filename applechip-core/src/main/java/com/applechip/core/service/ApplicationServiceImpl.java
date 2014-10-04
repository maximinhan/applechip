package com.applechip.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.applechip.core.entity.CodeValue;
import com.applechip.core.entity.code.Category;
import com.applechip.core.entity.code.Detail;
import com.applechip.core.repository.ApplicationRepository;
import com.applechip.core.util.ApplicationUtil;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  @Autowired
  private ApplicationRepository applicationRepository;

  @Autowired
  private ApplicationUtil applicationUtil;

  @Override
  @Cacheable(value = "getCategories")
  public List<CodeValue> getCategories() {
    List<Category> codeCategories = applicationRepository.getCategories();
    List<CodeValue> list = new ArrayList<CodeValue>();
    for (Category codeCategory : codeCategories) {
      list.add(new CodeValue(codeCategory.getId(), applicationUtil.getMessage(codeCategory.getId())));
    }
    return list;
  }

  @Override
  @Cacheable(value = "getDetails", key = "#category")
  public List<CodeValue> getDetails(String category) {
    List<Detail> codes = applicationRepository.getDetails(category);
    List<CodeValue> list = new ArrayList<CodeValue>();
    for (Detail code : codes) {
      list.add(new CodeValue(code.getId().getId(), applicationUtil.getMessage(code.getId().getId())));
    }
    return list;
  }

  @Override
  public Map<String, String> getGeoipGroupMap() {
    return applicationRepository.getGeoipGroupMap();
  }
}
