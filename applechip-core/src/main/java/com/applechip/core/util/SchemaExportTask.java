package com.applechip.core.util;

import lombok.extern.apachecommons.CommonsLog;

import org.gradle.api.internal.ConventionTask;

 @CommonsLog
 public class SchemaExportTask extends ConventionTask {
//
// private Boolean format = Boolean.TRUE;
// private Boolean comments = Boolean.TRUE;
// private String dialects = "org.hibernate.dialect.MySQL5Dialect";
// private String persistenceUnitName = "default";
// private String delimiter = ";";
// private String targetDirectory = "build/ddl";
//
// public String getTargetDirectory() {
// return targetDirectory;
// }
//
// public void setTargetDirectory(String targetDirectory) {
// this.targetDirectory = targetDirectory;
// }
//
// public Boolean getFormat() {
// return format;
// }
//
// public void setFormat(Boolean format) {
// this.format = format;
// }
//
// public Boolean getComments() {
// return comments;
// }
//
// public void setComments(Boolean comments) {
// this.comments = comments;
// }
//
// public String getDialects() {
// return dialects;
// }
//
// public void setDialects(String dialects) {
// this.dialects = dialects;
// }
//
// public String getPersistenceUnitName() {
// return persistenceUnitName;
// }
//
// public void setPersistenceUnitName(String persistenceUnitName) {
// this.persistenceUnitName = persistenceUnitName;
// }
//
// public String getDelimiter() {
// return delimiter;
// }
//
// public void setDelimiter(String delimiter) {
// this.delimiter = delimiter;
// }
//
// private FileCollection classpath;
//
// /**
// * Returns the classpath for the Hibernate Tools
// */
// @InputFiles
// public FileCollection getClasspath() {
// return classpath;
// }
//
// /**
// * Set the classpath for the web application
// */
// public void setClasspath(FileCollection classpath) {
// this.classpath = classpath;
// }
//
//
// @org.gradle.api.tasks.TaskAction
// protected void start() {
// ClassLoader originalClassloader = this.getClass().getClassLoader();
// try {
// List<URL> classpath = setUpClassPath();
// URLClassLoader hibernateToolsClassloader = new URLClassLoader(classpath.toArray(new
// URL[classpath.size()]), originalClassloader);
// Thread.currentThread().setContextClassLoader(hibernateToolsClassloader);
// exportSchema();
// } catch (MalformedURLException e) {
// log.error("Error trying to set the Hibernate Tools classpath");
// } finally {
// Thread.currentThread().setContextClassLoader(originalClassloader);
// }
// }
//
//
// @SuppressWarnings("unchecked")
// private void exportSchema() {
// Ejb3Configuration configuration = new Ejb3Configuration();
// Map properties = new HashMap();
// properties.put("hibernate.format_sql", format);
// properties.put("hibernate.use_sql_comments", comments);
// configuration = configuration.configure(persistenceUnitName, properties);
//
// if (configuration == null) {
// log.error("Error: Unable to export schema");
// return;
// }
//
// for (Iterator iterator = configuration.getClassMappings(); iterator.hasNext();) {
// PersistentClass pc = (PersistentClass) iterator.next();
// log.info("Found persistent class: " + pc.getClassName());
// }
//
// for (final String dialect : getDialectList()) {
// try {
// export(configuration, dialect);
// } catch (final Exception e) {
// log.error("Error exporting DDL for dialect " + dialect, e);
// }
// }
// }
//
// private void export(final Ejb3Configuration configuration, final String dialect) {
// if ((configuration == null) || (dialect == null)) {
// return;
// }
//
// log.info("Generating DDL for " + getDatabaseName(dialect));
// configuration.setProperty("hibernate.dialect", dialect);
//
// SchemaExport export = new SchemaExport(configuration.getHibernateConfiguration());
// export.setDelimiter(delimiter);
// export.setFormat(format);
// export.setHaltOnError(true);
// // just drop
// export.setOutputFile(getOutputFile(dialect, false, true).getAbsolutePath());
// export.execute(false, false, true, false);
// // just create
// export.setOutputFile(getOutputFile(dialect, true, false).getAbsolutePath());
// export.execute(false, false, false, true);
// // drop and create
// export.setOutputFile(getOutputFile(dialect, true, true).getAbsolutePath());
// export.execute(false, false, false, false);
// }
//
// private File getOutputFile(final String dialect, final boolean create, final boolean drop) {
// if (dialect == null) {
// return null;
// }
//
// String dbname = getDatabaseName(dialect).toLowerCase();
//
// File ddlDir = new File(getTargetDirectory());
// File dbDir = new File(ddlDir, dbname);
// dbDir.mkdirs();
//
// StringBuilder filename = new StringBuilder();
//
// if (drop) {
// filename.append("drop");
// }
//
// if (drop && create) {
// filename.append("_");
// }
//
// if (create) {
// filename.append("create");
// }
//
// filename.append(".sql");
//
// File targetFile = new File(dbDir, filename.toString());
//
// return targetFile;
// }
//
// private String getDatabaseName(final String dialect) {
// if (dialect == null) {
// return null;
// }
//
// String dbname = dialect.replaceAll("org.hibernate.dialect.", "");
// dbname = dbname.replaceAll("Dialect", "");
//
// return dbname;
// }
//
// private List<String> getDialectList() {
// ArrayList<String> dialectList = new ArrayList<String>();
// StringTokenizer tokenizer = new StringTokenizer(getDialects(), ",");
//
// while (tokenizer.hasMoreTokens()) {
// dialectList.add(tokenizer.nextToken());
// }
//
// return dialectList;
// }
//
//
// private List<URL> setUpClassPath() throws MalformedURLException {
// List<URL> classPathElements = new ArrayList<URL>();
// for (File file : getClasspath().getFiles()) {
// classPathElements.add(file.toURI().toURL());
// }
//
//
// if (log.isDebugEnabled()) {
// for (URL element : classPathElements) {
// log.debug("classpath element: " + element.toExternalForm());
// }
// }
// return classPathElements;
// }
//
 }
