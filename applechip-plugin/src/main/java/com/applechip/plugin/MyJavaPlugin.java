package com.applechip.plugin;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.DomainObjectCollection;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.internal.IConventionAware;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.ConventionValue;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.scala.ScalaCompile;
import org.gradle.util.WrapUtil;

public class MyJavaPlugin implements Plugin<Project> {

  @Override
  public void apply(Project target) {
    target.getPlugins().apply(JavaPlugin.class);

    // target.task("javaTask");

    // HibernateToolsConvention hibernateToolsConvention = new HibernateToolsConvention();
    // Convention convention = target.getConvention();
    // convention.getPlugins().put("hibernateTools", hibernateToolsConvention);
    // target.task(arg0)
    // target.getTasks().create("javaTask", MyJavaTask.class);
    // task ddl(type: HibernateJpaSchemaGenerationTask, dependsOn: [compileJava, processResources])
    // jar.dependsOn << ddl

    // List<Action<Task>> list = new ArrayList<Action<Task>>();
    // list.add(new Action<Task>() {
    // @Override
    // public void execute(Task compileTask) {
    // System.out.println("111111111111111111111111111111111111111111");
    // compileTask.dependsOn(Jar.class);
    // }
    // });


    // target.getTasks().create("javaTask", MyJavaTask.class);
    // .setDependsOn(WrapUtil.toSet(JavaCompile.TASK_DEPENDS_ON));
    
//    target.getTasks().withType(JavaCompile.class, new Action<JavaCompile>() {
//      @Override
//      public void execute(JavaCompile compileTask) {
//        System.out.println("111111111111111111111111111111111111111111");
//        compileTask.dependsOn(MyJavaTask.class);
//      }
//    });
//    target.getTasks().create("javaTask", MyJavaTask.class);
    
//    convention.convention.plugins.dbdeploy
    target.getExtensions().create("hibernateTools", HibernateToolsConvention.class);
    
    configureBuildDependents(target);
  }

  // private void configureCompile(final Project project, JavaPlugin javaPlugin) {
  // project.getTasks().withType(ScalaCompile.class).allTasks(new Action<ScalaCompile>() {
  // public void execute(ScalaCompile compile) {
  // compile.conventionMapping("scalaSrcDirs", new ConventionValue() {
  // public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
  // return scala(convention).getScalaSrcDirs();
  // }
  // });
  // compile.setDependsOn(WrapUtil.toSet(SCALA_DEFINE_TASK_NAME));
  // }
  // });
  // ScalaCompile scalaCompile = project.getTasks().replace(COMPILE_TASK_NAME, ScalaCompile.class);
  // javaPlugin.configureForSourceSet(SourceSet.MAIN_SOURCE_SET_NAME, scalaCompile);
  // scalaCompile.setDescription("Compiles the Scala source code.");
  // }

  private void configureBuildDependents(Project project) {
    Task buildTask = project.getTasks().create("javaTask", MyJavaTask.class);
//    buildTask.getConvention().add("", "");
//    buildTask.getConvention().findByName("format") = convention.getFormat();
//    buildTask.setDescription("Assembles and tests this project and all projects that depend on it.");
//    buildTask.setGroup(BasePlugin.BUILD_GROUP);
//    buildTask.dependsOn(Jar.DEFAULT_EXTENSION);
  }
  
  public JavaPluginConvention getJavaConvention(Project project) {
    return project.getConvention().getPlugin(JavaPluginConvention.class);
  }
}
