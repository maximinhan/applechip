package com.applechip.core.repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.jdbc.datasource.ConnectionHandle;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionDefinition;

public class CustomHibernateJpaVendorAdapter extends HibernateJpaVendorAdapter {
  private final JpaDialect jpaDialect = new CustomHibernateJpaDialect();

  @Override
  public JpaDialect getJpaDialect() {
    return this.jpaDialect;
  }

  @Slf4j
  private static class CustomHibernateJpaDialect extends HibernateJpaDialect {
    private static final long serialVersionUID = -2162607544160231149L;

    @Override
    public ConnectionHandle getJdbcConnection(EntityManager entityManager, boolean readOnly) throws PersistenceException, SQLException {
      ConnectionHandle connectionHandle = super.getJdbcConnection(entityManager, readOnly);
      if (null != connectionHandle && null != connectionHandle.getConnection()) {
        if (readOnly) {
          connectionHandle.getConnection().setReadOnly(true);
          log.debug("readOnly... connection info: {}", connectionHandle.getConnection());
        } else {
          connectionHandle.getConnection().setReadOnly(false);
          log.debug("writeOnly... connection info: {}", connectionHandle.getConnection());
        }
      }
      return connectionHandle;
    }

    @Override
    public Object beginTransaction(EntityManager entityManager, final TransactionDefinition transactionDefinition) {
      Session session = getSession(entityManager);
      if (TransactionDefinition.TIMEOUT_DEFAULT != transactionDefinition.getTimeout()) {
        session.getTransaction().setTimeout(transactionDefinition.getTimeout());
      }
      final Data data = new Data();
      session.doWork(new Work() {
        @Override
        public void execute(Connection connection) throws SQLException {
          Integer old = DataSourceUtils.prepareConnectionForTransaction(connection, transactionDefinition);
          log.debug("old: {}, new: {}", old, transactionDefinition.getIsolationLevel());
          data.setisolationLevel(old);
          data.setConnection(connection);
        }
      });
      entityManager.getTransaction().begin();
      Object transactionData = super.prepareTransaction(entityManager, transactionDefinition.isReadOnly(), transactionDefinition.getName());
      data.setData(transactionData);
      return data;
    }

    @Override
    public void cleanupTransaction(Object object) {
      Data data = (Data) object;
      super.cleanupTransaction(data.getData());
      data.reset();
    }

    private static class Data {

      private Object data;

      private Integer isolationLevel;

      private Connection connection;

      public Data() {}

      public void reset() {
        if (null != this.connection && null != this.isolationLevel) {
          DataSourceUtils.resetConnectionAfterTransaction(connection, isolationLevel);
        }
      }

      public Object getData() {
        return this.data;
      }

      public void setData(Object data) {
        this.data = data;
      }

      public void setisolationLevel(Integer isolationLevel) {
        this.isolationLevel = isolationLevel;
      }

      public void setConnection(Connection connection) {
        this.connection = connection;
      }
    }
  }
}
