package com.applechip.core.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.applechip.core.AbstractTransactionTest;
import com.applechip.core.entity.User;
import com.applechip.core.repository.UserRepository;

public class CacheServiceImplTest extends AbstractTransactionTest {

  // @PersistenceContext(unitName = BaseConstant.PERSISTENCE_UNIT_NAME)
  // public EntityManager entityManager;

  // @Autowired
  // private LookupManager lookupManager;
//  @Autowired
//  private UserRepository userRepository;

  @Test
  public void testGetRoles() throws Exception {
    System.out.println("ss");
  }

//  @Test
//  public void testCrud() {
//    User entity = new User();
//    entity.setId("vicki");
//    entity.setUsername("123");
//    userRepository.save(entity);
//  }


  @Test
  @Ignore
  public void testGetCode() {
    // JPAQuery query = new JPAQuery(entityManager);
    // QCode code = QCode.code;
    // query.from(code);
    // List<Category> dd = query.list(code);
    // log.debug(dd);
    // return query.singleResult(code);
  }

  @Test
  @Ignore
  public void testsss() {
    // log.debug(LocaleContextHolder.getLocale());
    // log.debug(lookupManager.getCodes("C00",
    // LocaleContextHolder.getLocale()));//CodesMap("Skill").get("Piano"));
  }
}
