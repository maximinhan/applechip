package com.applechip.core.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.entity.code.Category;
import com.applechip.core.entity.code.Detail;
import com.applechip.core.entity.code.QCategory;
import com.applechip.core.entity.code.QDetail;
import com.applechip.core.entity.network.QGeoipGroup;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class ApplicationRepositoryImpl implements ApplicationRepository {

  @PersistenceContext(unitName = ApplicationConstant.PERSISTENCE_UNIT_NAME)
  private EntityManager entityManager;

  @Override
  public List<Category> getCategories() {
    JPAQuery query = new JPAQuery(entityManager);
    QCategory category = QCategory.category;
    query.from(category).where(category.enabled.eq(true));
    return query.list(category);
  }

  @Override
  public List<Detail> getDetails(String categoryId) {
    JPAQuery query = new JPAQuery(entityManager);
    QDetail detail = QDetail.detail;
    query.from(detail).where(detail.id.categoryId.eq(categoryId).and(detail.enabled.eq(true))).orderBy(detail.sequence.asc());
    return query.list(detail);
  }

  @Override
  public Map<String, String> getGeoipGroupMap() {
    JPAQuery query = new JPAQuery(entityManager);
    QGeoipGroup geoipGroup = QGeoipGroup.geoipGroup;
    query.from(geoipGroup);
    List<Tuple> tuples = query.list(geoipGroup.id.geoipId, geoipGroup.id.region, geoipGroup.group.id);
    Map<String, String> map = new HashMap<String, String>();
    for (Tuple row : tuples) {
      String geoipId = row.get(geoipGroup.id.geoipId);
      String region = row.get(geoipGroup.id.region);
      String groupId = row.get(geoipGroup.group.id);
      String key = geoipId.concat("_").concat(region);
      map.put(key, groupId);
    }
    return map;
  }
}
