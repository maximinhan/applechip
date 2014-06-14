package com.applechip.core.abstact;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GenericResponse<T> extends ResponseVO {

  private static final long serialVersionUID = 923800543461020152L;
  protected final transient Log log = LogFactory.getLog(getClass());

  @JsonProperty("list")
  @XmlElement(name = "list")
  private List<T> list;

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public abstract Class<?> getDataClass();
}
