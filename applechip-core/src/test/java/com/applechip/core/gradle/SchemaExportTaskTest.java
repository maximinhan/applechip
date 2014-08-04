package com.applechip.core.gradle;

import java.util.Set;

import lombok.extern.apachecommons.CommonsLog;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@CommonsLog
@RunWith(MockitoJUnitRunner.class)
public class SchemaExportTaskTest {

  private Project project;

  private Set<Task> taskSet;

  @Before
  public void setUp() {
    project = ProjectBuilder.builder().build();
    log.warn(project.getName());
    log.warn(project.getGroup());
    log.warn(project.getVersion());
    log.warn(project.getBuildDir());
    log.warn(project.getStatus());
    log.warn(project.getParent());
    taskSet = project.getTasksByName("build", Boolean.TRUE);
  }

  @Test
  public void taskCreatedProperly() {
    log.warn("test");
    for (Task task : taskSet){
      log.warn(task.getName());
      Assert.assertTrue(task instanceof SchemaExportTask);
    }
  }

  @Test
  public void shouldRunTask() {
    // task.execute() // how to run this task? I want to run build() method from MyCustomTask class
    // which is @TaskAction
  }


}
