package com.applechip.core.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Ignore;
import org.junit.Test;

import com.applechip.core.AbstractTransactionTest;
import com.applechip.core.constant.BaseConstant;

public class CacheServiceImplTest extends AbstractTransactionTest {// AbstractTest,
                                                                   // AbstractTransactionTest

  @PersistenceContext(unitName = BaseConstant.PERSISTENCE_UNIT_NAME)
  public EntityManager entityManager;
  
//  @Autowired
//  private LookupManager lookupManager;
  
  @Test
  public void testGetRoles() throws Exception {
    log.debug("ddd");
  }
  
  @Test
  @Ignore
  public void testGetCode(){
//    JPAQuery query = new JPAQuery(entityManager);
//    QCode code = QCode.code;
//    query.from(code);
//    List<Category> dd = query.list(code);
//    log.debug(dd);
//    return query.singleResult(code);
  }
  
  @Test
  @Ignore
  public void testsss(){
//    log.debug(LocaleContextHolder.getLocale());
//    log.debug(lookupManager.getCodes("C00", LocaleContextHolder.getLocale()));//CodesMap("Skill").get("Piano"));
  }
}
