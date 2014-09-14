package com.applechip.core.configurer.support;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import com.applechip.core.abstact.GenericEntity;
import com.applechip.core.entity.User;

@Component
@Slf4j
public class CustomEventListener implements PostInsertEventListener, PostUpdateEventListener {

  private static final long serialVersionUID = 2714692810503179272L;

  @Override
  public void onPostUpdate(PostUpdateEvent event) {}

  @Override
  public void onPostInsert(PostInsertEvent event) {
    Object object = event.getEntity();
    if (!needWriteLog(object)) {
      return;
    }
    Session session = event.getSession().getSessionFactory().openSession();
    try {
      writeLog(session, object);
    } catch (HibernateException ex) {
      log.error(String.format("onPostInsert error: {}, message: {}", object, ex.getMessage()));
    } finally {
      if (session.isOpen())
        session.close();
    }
  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister persister) {
    return true;
  }

  private boolean needWriteLog(Object entity) {
    boolean result = (entity instanceof User);
    return result;
  }

  private void writeLog(Session session, Object object) {
    if (object instanceof User) {
      User user = (User) object;
      saveUserLog(session, user);
    }
  }

  private void saveUserLog(Session session, User user) {
    // BeanUtils.copyProperties(user, helpLog);
    // save(session, helpLog);
  }

  private void save(Session session, GenericEntity<?> entity) {
    Transaction transaction = null;
    try {
      transaction = session.beginTransaction();
      session.saveOrUpdate(entity);
      transaction.commit();
    } catch (HibernateException ex) {
      if (transaction != null)
        transaction.rollback();
      throw ex;
    }
  }

}
