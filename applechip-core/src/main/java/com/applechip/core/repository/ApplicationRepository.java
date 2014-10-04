package com.applechip.core.repository;

import java.util.List;
import java.util.Map;

import com.applechip.core.entity.code.Category;
import com.applechip.core.entity.code.Detail;

public interface ApplicationRepository {

  List<Category> getCategories();

  List<Detail> getDetails(String codeCategory);

  Map<String, String> getGeoipGroupMap();

}
