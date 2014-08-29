package com.applechip.core.repository;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import lombok.Getter;
import lombok.Setter;
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

  private static CustomHibernateJpaVendorAdapter customHibernateJpaVendorAdapter;

  private CustomHibernateJpaVendorAdapter() {}

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
      if (connectionHandle != null && connectionHandle.getConnection() != null) {
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
      Session session = super.getSession(entityManager);
      if (TransactionDefinition.TIMEOUT_DEFAULT != transactionDefinition.getTimeout()) {
        session.getTransaction().setTimeout(transactionDefinition.getTimeout());
      }
      final Data data = new Data();
      session.doWork(new Work() {
        @Override
        public void execute(Connection connection) throws SQLException {
          Integer isolationLevel = DataSourceUtils.prepareConnectionForTransaction(connection, transactionDefinition);
          log.debug("old: {}, new: {}", isolationLevel, transactionDefinition.getIsolationLevel());
          data.setIsolationLevel(isolationLevel);
          data.setConnection(connection);
        }
      });
      entityManager.getTransaction().begin();
      data.setObject(super.prepareTransaction(entityManager, transactionDefinition.isReadOnly(), transactionDefinition.getName()));
      return data;
    }

    @Override
    public void cleanupTransaction(Object object) {
      Data data = (Data) object;
      super.cleanupTransaction(data.getObject());
      data.reset();
    }

    @Getter
    @Setter
    @SuppressWarnings(value = {"PMD.UnusedPrivateField", "PMD.SingularField"})
    private static class Data {

      private Object object;

      private Integer isolationLevel;

      private Connection connection;

      public void reset() {
        if (this.connection != null && this.isolationLevel != null) {
          DataSourceUtils.resetConnectionAfterTransaction(connection, isolationLevel);
        }
      }
    }
  }

  public static CustomHibernateJpaVendorAdapter getInstance() {
    if (customHibernateJpaVendorAdapter == null) {
      customHibernateJpaVendorAdapter = new CustomHibernateJpaVendorAdapter();
    }
    return customHibernateJpaVendorAdapter;
  }
}
