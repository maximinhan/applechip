package com.applechip.core.entity;

import java.io.Serializable;

import com.applechip.core.AbstractObject;

@SuppressWarnings("serial")
public abstract class GenericEntity<PK extends Serializable> extends AbstractObject {
  public abstract String toString();

  public abstract PK getId();
}
