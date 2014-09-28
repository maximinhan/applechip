package com.applechip.plugin;

import java.io.File;
import java.sql.DatabaseMetaData;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.StringUtils;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class ApplechipPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getPlugins().apply(JavaPlugin.class);
		Task task = project.task("hibernateTask");
		task.dependsOn(project.getTasks().toArray());
		HibernateSetting hibernateSetting = project.getExtensions().create("hibernateSetting", HibernateSetting.class);
		hibernateSetting.setSkip(Boolean.FALSE);
		hibernateSetting.setLineSeparator("");
		configureSchemaExport(project);
		// project.
//		project.getConfigurations().withType(HibernateSetting.class)hibernateSetting;
		project.getTasks().create("hibernateTask", HibernateTask.class);
	}

	private void configureSchemaExport(final Project project) {
		project.getTasks().withType(HibernateTask.class).whenTaskAdded(new Action<HibernateTask>() {
			public void execute(HibernateTask hibernateTask) {
				hibernateTask.setClasspath(getJavaConvention(project).getSourceSets()
						.getByName(SourceSet.MAIN_SOURCE_SET_NAME).getRuntimeClasspath());
			}
		});
	}

	public JavaPluginConvention getJavaConvention(Project project) {
		return project.getConvention().getPlugin(JavaPluginConvention.class);
	}
}

@Getter
@Setter
class HibernateSetting {
	final String name;

	boolean skip = false;

	boolean format = false;

	boolean scanTestClasses = false;

	String persistenceUnitName;

	File outputDirectory;

	String createOutputFileName;

	String dropOutputFileName;

	File createSourceFile;

	File dropSourceFile;

	String jdbcDriver;

	String jdbcUrl;

	String jdbcUser;

	String jdbcPassword;

	String databaseProductName;

	Integer databaseMajorVersion;

	Integer databaseMinorVersion;

	String namingStrategy;

	String dialect;

	String lineSeparator;

	public HibernateSetting() {
		this(null);
	}

	public HibernateSetting(String name) {
		this.name = name;
	}

	public HibernateSetting(String name, HibernateSetting base, HibernateSetting target) {
		 this(name);
		//
		// this.skip = (target?.skip ?: false) ? target.skip : base.skip
		// this.format = (target?.format ?: false) ? target.format : base.format
		// this.scanTestClasses = (target?.scanTestClasses ?: false) ? target.scanTestClasses : base.scanTestClasses
		//
		// this.persistenceUnitName = target?.persistenceUnitName ?: base.persistenceUnitName
		//
		// this.scriptAction = target?.scriptAction ?: base.scriptAction
		//
		// this.outputDirectory = target?.outputDirectory ?: base.outputDirectory
		// this.createOutputFileName = target?.createOutputFileName ?: base.createOutputFileName
		// this.dropOutputFileName = target?.dropOutputFileName ?: base.dropOutputFileName
		//
		// this.createSourceMode = target?.createSourceMode ?: base.createSourceMode
		// this.createSourceFile = target?.createSourceFile ?: base.createSourceFile
		// this.dropSourceMode = target?.dropSourceMode ?: base.dropSourceMode
		// this.dropSourceFile = target?.dropSourceFile ?: base.dropSourceFile
		//
		this.jdbcDriver = StringUtils.defaultString(target.getJdbcDriver(), base.getJdbcDriver());
		this.jdbcUrl = StringUtils.defaultString(target.getJdbcUrl(), base.getJdbcUrl());
		this.jdbcUser = StringUtils.defaultString(target.getJdbcUser(), base.getJdbcUser());
		this.jdbcPassword = StringUtils.defaultString(target.getJdbcPassword(), base.getJdbcPassword());
		//
		// this.databaseProductName = target?.databaseProductName ?: base.databaseProductName
		// this.databaseMajorVersion = target?.databaseMajorVersion ?: base.databaseMajorVersion
		// this.databaseMinorVersion = target?.databaseMinorVersion ?: base.databaseMinorVersion
		//
		// this.namingStrategy = target?.namingStrategy ?: base.namingStrategy
		// this.dialect = target?.dialect ?: base.dialect
		//
		// this.lineSeparator = target?.lineSeparator ?: base.lineSeparator
	}
}
