package com.applechip.core.service;

import java.util.List;
import java.util.Map;

import com.applechip.core.entity.CodeValue;

public interface ApplicationService {

  List<CodeValue> getCategories();

  List<CodeValue> getDetails(String codeCategory);

  Map<String, String> getGeoipGroupMap();
}
