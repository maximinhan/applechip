package com.applechip.core.entity;

import java.io.Serializable;

import com.applechip.core.AbstractObject;
// @Entity
// @DiscriminatorValue(CommonCodes.CompanyType.PARTNER)
// @Audited(auditParents = Company.class)
// @EntityListeners({ AuditingEntityListener.class })

@SuppressWarnings("serial")
public abstract class GenericEntity<PK extends Serializable> extends AbstractObject {
  // @CreatedDate
  // @LastModifiedDate
  // @CreatedBy
  // @LastModifiedBy
  public abstract String toString();

  public abstract PK getId();
}
