package com.applechip.core.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class ListVO<T> extends ResponseVO {
  private static final long serialVersionUID = -6912212857026691229L;
  @JsonProperty("list")
  @XmlElement(name = "list")
  private List<T> list;

  public Class<?> getDataClass() {
    Class<?> clazz = null;
    if (CollectionUtils.isNotEmpty(this.list))
      clazz = this.list.iterator().next().getClass();
    return clazz;
  };

}
