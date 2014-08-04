package com.applechip.plugin;

import lombok.Getter;
import lombok.Setter;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

@Getter
@Setter
public class MyJavaTask extends DefaultTask {

  private Boolean format = Boolean.FALSE;

  @TaskAction
  public void javaTask() {
    System.out.println("format" + format);
    HibernateToolsConvention convention = (HibernateToolsConvention) getProject().getExtensions().getByName("hibernateTools");
    System.out.println("convention.getFormat()" + convention.getFormat());
    this.format = convention.getFormat();
    System.out.println("Hello from MyJavaTask");
    System.out.println("this.format" + format);
  }

}
