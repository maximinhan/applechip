package com.applechip.core.abstact;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class GenericEntity<PK extends Serializable> extends AbstractObject {
  public abstract String toString();

  public abstract PK getId();
}
