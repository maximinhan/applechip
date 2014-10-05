package com.applechip.core.entity;

import java.io.Serializable;

import com.applechip.core.AbstractObject;
//@Entity
//@DiscriminatorValue(CommonCodes.CompanyType.PARTNER)
//@Audited(auditParents = Company.class)
@SuppressWarnings("serial")
public abstract class GenericEntity<PK extends Serializable> extends AbstractObject {
  public abstract String toString();

  public abstract PK getId();
}
