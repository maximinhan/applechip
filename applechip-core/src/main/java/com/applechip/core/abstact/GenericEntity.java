package com.applechip.core.abstact;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public abstract class GenericEntity<PK extends Serializable> extends AbstractObject {
  public abstract String toString();

  public abstract PK getId();
}