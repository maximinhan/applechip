package com.applechip.core.configurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.applechip.core.repository.EntityListener;

@Component
public class CustomEntityListenerConfigurer {
  @Resource
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  private EntityListener entityListener;

  @PostConstruct
  public void registerListeners() {
    HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) entityManagerFactory;
    SessionFactory sessionFactory = hibernateEntityManagerFactory.getSessionFactory();
    EventListenerRegistry eventListenerRegistry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);

    eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_INSERT).appendListener(entityListener);
    eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE).appendListener(entityListener);
  }
}