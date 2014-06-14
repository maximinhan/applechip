package com.applechip.core.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
public class EntityListener implements PostInsertEventListener, PostUpdateEventListener {

  private static final long serialVersionUID = 2714692810503179272L;
  private transient Log log = LogFactory.getLog(EntityListener.class);

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
//    Object object = event.getEntity();
//    if (!needWriteLog(object)) {
//      return;
//    }
//    Session session = event.getSession().getSessionFactory().openSession();
//    try {
//      writeLog(session, object);
//    } catch (Exception ex) {
//      log.error(String.format("onPostInsert Error::%s:%s", object, ex.getMessage()));
//    } finally {
//      if (session.isOpen())
//        session.close();
//    }
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
//    Object object = event.getEntity();
//    if (!needWriteLog(object)) {
//      return;
//    }
//    Session session = event.getSession().getSessionFactory().openSession();
//    try {
//      writeLog(session, object);
//    } catch (Exception ex) {
//      log.error(String.format("onPostInsert Error::%s:%s", object, ex.getMessage()));
//    } finally {
//      if (session.isOpen())
//        session.close();
//    }
  }

  @Override
  public boolean requiresPostCommitHanding(EntityPersister persister) {
    return true;
  }

//  private boolean needWriteLog(Object entity) {
//    boolean result = (entity instanceof Help);
//    return result;
//  }
//
//  private void writeLog(Session session, Object object) {
//    if (object instanceof Help) {
//      Help help = (Help) object;
//      saveHelpLog(session, help);
//    }
//  }
//
//  private void save(Session session, GenericEntity<?> domain) {
//    Transaction tx = null;
//    try {
//      tx = session.beginTransaction();
//      session.saveOrUpdate(domain);
//      tx.commit();
//    } catch (HibernateException ex) {
//      if (tx != null)
//        tx.rollback();
//      throw ex;
//    }
//  }
//
//  private void saveHelpLog(Session session, Help help) {
//    if (help.getRequest() == null) {
//      log.warn(String.format("help.getRequest() is null...helpId=%s", help.getId()));
//      return;
//    }
//
//    HelpLog helpLog = (HelpLog) session.get(HelpLog.class, help.getId());
//    if (helpLog == null) {
//      helpLog = new HelpLog();
//      helpLog.setId(help.getId());
//      helpLog.setRequest(help.getRequest().getUser().getUsername());
//      helpLog.setCategory(help.getCategories().iterator().next());
//    } else {
//      helpLog.setResponse(help.getResponse().getUser().getUsername());
//    }
//
//    BeanUtils.copyProperties(help, helpLog);
//
//    save(session, helpLog);
//
//  }

}