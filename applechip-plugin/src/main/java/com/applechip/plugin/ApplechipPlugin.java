package com.applechip.plugin;

import groovy.lang.Closure;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.internal.IConventionAware;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;

public class ApplechipPlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getPlugins().apply(JavaPlugin.class);
    project.getExtensions().create("applechipSetting", ApplechipSetting.class);
    configureSchemaExport(project);
    project.getTasks().create("hibernateTask", HibernateTask.class);
  }

  private void configureSchemaExport(final Project project) {
    project.getTasks().withType(HibernateTask.class).whenTaskAdded(new Action<HibernateTask>() {
      public void execute(HibernateTask hibernateTask) {
        hibernateTask.setClasspath(getJavaConvention(project).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME).getRuntimeClasspath());
      }
    });
  }

  public JavaPluginConvention getJavaConvention(Project project) {
    return project.getConvention().getPlugin(JavaPluginConvention.class);
  }
}
