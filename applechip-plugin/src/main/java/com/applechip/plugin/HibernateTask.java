package com.applechip.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskAction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.applechip.plugin.properties.HibernateProperties;

@Slf4j
@Getter
@Setter
public class HibernateTask extends DefaultTask {

  private Boolean format = Boolean.TRUE;
  private String delimiter = ";";

  private FileCollection classpath;

  @InputFiles
  public FileCollection getClasspath() {
    return classpath;
  }

  private List<URL> setUpClassPath() throws MalformedURLException {
    List<URL> classPathElements = new ArrayList<URL>();
    int index = 0;
    for (File file : getClasspath().getFiles()) {
      classPathElements.add(file.toURI().toURL());
      ++index;
    }
    System.out.println("counth element: " + index);
    for (File file : getFile()) {
      classPathElements.add(file.toURI().toURL());
      ++index;
    }
    System.out.println("counth element: " + index);
    for (URL element : classPathElements) {
      System.out.println("classpath element: " + element.toExternalForm());
    }
    // this.class.classLoader.rootLoader.addURL( new URL("file:///c:/.../postgresql-xxx.jar") )
    if (log.isDebugEnabled()) {
      for (URL element : classPathElements) {
        log.debug("classpath element: " + element.toExternalForm());
      }
    }
    classPathElements = new ArrayList<URL>(new HashSet<URL>(classPathElements));
    return classPathElements;
  }

  private Set<File> getFile() {
    return getProject().getConfigurations().getByName("compile").getFiles();
  }

  @TaskAction
  public void hibernateTask() {
    ApplechipSetting applechipSetting = (ApplechipSetting) getProject().getExtensions().getByName("applechipSetting");
    log.debug("hibernateTask start");
    log.debug("{}", applechipSetting.toString());
    System.out.println("hibernateTask start");
    System.out.println(applechipSetting.toString());

    this.classpath =
        getProject().getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            .getRuntimeClasspath();
    // this.classpath.add(getProject().getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME)
    // .getCompileClasspath());
    this.classpath.add(getProject().getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME)
        .getOutput().getDirs());

    // ClassLoader originalClassloader = this.getClass().getClassLoader();
    ClassLoader previousClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      List<URL> classpath = setUpClassPath();
      URLClassLoader hibernateToolsClassloader = new URLClassLoader(classpath.toArray(new URL[classpath.size()]), getClass().getClassLoader());
      Thread.currentThread().setContextClassLoader(hibernateToolsClassloader);
      exportSchema(HibernateProperties.getProperties(applechipSetting));
    } catch (MalformedURLException e) {
      log.error("Error trying to set the Hibernate Tools classpath");
    } finally {
      Thread.currentThread().setContextClassLoader(previousClassLoader);
    }
  }

  private void exportSchema(Properties properties) {
    Configuration configuration = new Configuration();
    configuration = configuration.setProperties(properties);
    if (configuration == null) {
      log.error("Error: Unable to export schema");
      return;
    }
    SchemaExport export = new SchemaExport(configuration);
    export.setDelimiter(delimiter);
    export.setFormat(format);
    export.setHaltOnError(true);
    export.execute(false, false, false, false);
  }
}
