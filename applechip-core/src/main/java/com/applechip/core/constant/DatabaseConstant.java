package com.applechip.core.constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.applechip.core.util.FieldUtil;

public class DatabaseConstant {

	public static Map<String, String> HIBERNATE_DIALECT_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				private static final long serialVersionUID = -5418896212085484154L;
				{
					put("MYSQL", "org.hibernate.dialect.MySQL5InnoDBDialect");
					put("MYSQL_REPLICATION", "org.hibernate.dialect.MySQL5InnoDBDialect");
					put("ORACLE", "org.hibernate.dialect.Oracle10gDialect");
					put("SQLSERVER", "org.hibernate.dialect.SQLServer2008Dialect");
				}
			});

	public static Map<String, String> JDBC_DRIVER_CLASS_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				private static final long serialVersionUID = -5418896212085484154L;
				{
					put("MYSQL", "com.mysql.jdbc.Driver");
					put("MYSQL_REPLICATION", "com.mysql.jdbc.ReplicationDriver");
					put("ORACLE", "oracle.jdbc.OracleDriver");
					put("SQLSERVER", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
				}
			});

	public static Map<String, String> JDBC_VALIDATION_QUERY_MAP = Collections
			.unmodifiableMap(new HashMap<String, String>() {
				private static final long serialVersionUID = -5418896212085484154L;
				{
					put("MYSQL", "SELECT 1");
					put("MYSQL_REPLICATION", "/* ping */ SELECT 1");
					put("ORACLE", "SELECT 1 FROM DUAL");
					put("SQLSERVER", "SELECT 1");
				}
			});

	public static Set<String> DATA_SOURCE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
		private static final long serialVersionUID = -5954861215952396300L;
		{
			addAll(FieldUtil.readFieldList(BasicDataSourceFactory.class, String.class));
		}
	});

	public static Set<String> HIBERNATE_PROPERTIES_SET = Collections.unmodifiableSet(new HashSet<String>() {
		private static final long serialVersionUID = -5954861215952396300L;
		{
			addAll(FieldUtil.readFieldList(org.hibernate.cfg.AvailableSettings.class, String.class));
			addAll(FieldUtil.readFieldList(org.hibernate.jpa.AvailableSettings.class, String.class));
			addAll(FieldUtil.readFieldList(org.hibernate.envers.configuration.EnversSettings.class, String.class));
		}
	});
}
