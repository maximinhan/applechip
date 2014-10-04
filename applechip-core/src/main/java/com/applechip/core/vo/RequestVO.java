package com.applechip.core.vo;

import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

import com.applechip.core.AbstractObject;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class RequestVO extends AbstractObject {

  private static final long serialVersionUID = -2414941729311122868L;

  @XmlTransient
  @JsonIgnore
  private String sessionkey;

  @XmlTransient
  @JsonIgnore
  private String tracekey;

}
