package com.applechip.core.service;

import java.util.List;
import java.util.Map;

import com.applechip.core.entity.code.Value;

public interface ApplicationService {

  List<Value> getCategories();

  List<Value> getDetails(String codeCategory);

  Map<String, String> getGeoipGroupMap();
}
