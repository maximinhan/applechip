package com.applechip.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.applechip.core.entity.code.Category;
import com.applechip.core.entity.code.Value;
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
  public List<Value> getCategories() {
    List<Category> codeCategories = applicationRepository.getCategories();
    List<Value> list = new ArrayList<Value>();
    for (Category codeCategory : codeCategories) {
      list.add(new Value(codeCategory.getId(), applicationUtil.getMessage(codeCategory.getId())));
    }
    return list;
  }

  @Override
  @Cacheable(value = "getDetails", key = "#category")
  public List<Value> getDetails(String category) {
    List<Detail> codes = applicationRepository.getDetails(category);
    List<Value> list = new ArrayList<Value>();
    for (Detail code : codes) {
      list.add(new Value(code.getId().getId(), applicationUtil.getMessage(code.getId().getId())));
    }
    return list;
  }

  @Override
  public Map<String, String> getGeoipGroupMap() {
    return applicationRepository.getGeoipGroupMap();
  }
}
