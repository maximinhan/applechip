package com.applechip.core.configurer.support;

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

	private CustomHibernateJpaVendorAdapter() {
	}

	@Override
	public JpaDialect getJpaDialect() {
		return this.jpaDialect;
	}

	@Slf4j
	private static class CustomHibernateJpaDialect extends HibernateJpaDialect {
		private static final long serialVersionUID = -2162607544160231149L;

		@Override
		public ConnectionHandle getJdbcConnection(EntityManager entityManager, boolean readOnly)
				throws PersistenceException, SQLException {
			ConnectionHandle connectionHandle = super.getJdbcConnection(entityManager, readOnly);
			if (connectionHandle != null && connectionHandle.getConnection() != null) {
				if (readOnly) {
					connectionHandle.getConnection().setReadOnly(true);
					log.debug("readOnly... connection info: {}", connectionHandle.getConnection());
				}
				else {
					connectionHandle.getConnection().setReadOnly(false);
					log.debug("writeOnly... connection info: {}", connectionHandle.getConnection());
				}
			}
			return connectionHandle;
		}

		@Override
		public Object beginTransaction(EntityManager entityManager, final TransactionDefinition transactionDefinition) {
			Session session = super.getSession(entityManager);
			if (transactionDefinition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
				session.getTransaction().setTimeout(transactionDefinition.getTimeout());
			}
			final Data data = new Data();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					Integer acceptIsolationLevel = transactionDefinition.getIsolationLevel();
					Integer connectionIsolationLevel = connection.getTransactionIsolation();
					//					Integer isolationLevel = DataSourceUtils.prepareConnectionForTransaction(connection, transactionDefinition);
					if (acceptIsolationLevel != connectionIsolationLevel) {
						//						acceptIsolationLevel != TransactionDefinition.ISOLATION_DEFAULT
						log.debug("session execute acceptIsolationLevel: {}, connectionIsolationLevel {} ",
								acceptIsolationLevel, connectionIsolationLevel);
						data.setConnection(connection);
						connection.setTransactionIsolation(acceptIsolationLevel);
					}
				}
			});
			entityManager.getTransaction().begin();
			data.setObject(super.prepareTransaction(entityManager, transactionDefinition.isReadOnly(),
					transactionDefinition.getName()));
			return data;
		}

		@Override
		public void cleanupTransaction(Object object) {
			Data data = (Data) object;
			super.cleanupTransaction(data.getObject());
			data.reset();
		}

		private static class Data {

			@Getter
			@Setter
			private Object object;

			private Connection connection;

			private Integer connectionIsolationLevel;

			public void setConnection(Connection connection) throws SQLException {
				this.connection = connection;
				this.connectionIsolationLevel = connection.getTransactionIsolation();
			}

			public void reset() {
				if (this.connectionIsolationLevel != null && this.connection != null) {
					DataSourceUtils.resetConnectionAfterTransaction(connection, connectionIsolationLevel);
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
