package com.applechip.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.AbstractCompile;


public class HibernateToolsPlugin implements Plugin<Project> {
  public static final String SCHEMA_EXPORT = "schemaExport";

  private Project project;

  @Override
  public void apply(Project project) {
    System.out.println("111111111111111111111111111111111111111111");
    project.getPlugins().apply(JavaPlugin.class);
    // Closure<V>
    this.project = project;
    project.setVersion("");
    project.setGroup("");
    project.getRepositories();
    project.getTasks().create("myplugintask", SchemaExportTask.class);
//    Integer.parseInt(s)

    SourceSet mainSourceSet = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets().getByName("main");
    project.getExtensions().create("plugin", SchemaExportTask.class, project);

    // project.getTasks().usePlugin(JavaPlugin.class, project);
    HibernateToolsConvention hibernateToolsConvention = new HibernateToolsConvention();
    Convention convention = project.getConvention();
    convention.getPlugins().put("hibernateTools", hibernateToolsConvention);
    configureSchemaExport(project, hibernateToolsConvention);
    project.getTasks().withType(AbstractCompile.class, new Action<Task>() {
      @Override
      public void execute(Task compileTask) {
        System.out.println("111111111111111111111111111111111111111111");
        compileTask.dependsOn(SchemaExportTask.class);
      }
    });
  }

  // public void apply(Project project) {
  // project.getPlugins().apply(JavaPlugin.class);
  // project.getPlugins().apply(MavenPlugin.class);
  // this.project = project;
  // project.setVersion("36.0");
  // project.setGroup("com.applechip.core");
  // // project.repositories {
  // // mavenRepo name: 'xxx', urls: "http://speke.xxx.co.in:8080/artifactory/xxx"
  // // }
  // project.getTasks().create("myplugintask", SchemaExportTask.class);
  // System.out.println("111111111111111111111111111111111111111111");
  // }

  private void configureSchemaExport(final Project project, final HibernateToolsConvention hibernateToolsConvention) {
    project.getTasks().withType(SchemaExportTask.class).whenTaskAdded(new Action<SchemaExportTask>() {
      public void execute(SchemaExportTask schemaExport) {
//         schemaExport.getConventionMapping().map("classpath", new Closure(schemaExport) {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return
//         getJavaConvention(project).getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME).getRuntimeClasspath();
//         }
//         });
//         schemaExport.getConventionMapping().map("format", new Closure(schemaExport) {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return hibernateToolsConvention.getFormat();
//         }
//         });
//         schemaExport.getConventionMapping().map("comments", new Closure(schemaExport) {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return hibernateToolsConvention.getComments();
//         }
//         });
//         schemaExport.getConventionMapping().map("dialects", new Closure(schemaExport) {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return hibernateToolsConvention.getDialects();
//         }
//         });
//         schemaExport.getConventionMapping().map("persistenceUnitName", new Closure(schemaExport)
//         {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return hibernateToolsConvention.getPersistenceUnitName();
//         }
//         });
//         schemaExport.getConventionMapping().map("delimiter", new Closure(schemaExport) {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return hibernateToolsConvention.getDelimiter();
//         }
//         });
//         schemaExport.getConventionMapping().map("targetDirectory", new Closure(schemaExport) {
//         public Object getValue(Convention convention, IConventionAware conventionAwareObject) {
//         return hibernateToolsConvention.getTargetDirectory();
//         }
//         });
      }
    });
    // SchemaExportTask schemaExport = project.getTasks().add(SCHEMA_EXPORT,
    // SchemaExportTask.class);
    // schemaExport.setDescription("Generates DDL scripts for your database from your JPA persistent classes.");
  }

  public JavaPluginConvention getJavaConvention(Project project) {
    return project.getConvention().getPlugin(JavaPluginConvention.class);
  }

}
