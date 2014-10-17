package com.applechip.core.entity;

import java.io.Serializable;
// @Entity
// @DiscriminatorValue(CommonCodes.CompanyType.PARTNER)
// @Audited(auditParents = Company.class)
// @EntityListeners({ AuditingEntityListener.class })

@SuppressWarnings("serial")
public abstract class GenericEntity<PK extends Serializable> extends AbstractEntity {
  // @CreatedDate
  // @LastModifiedDate
  // @CreatedBy
  // @LastModifiedBy
  public abstract String toString();

  public abstract PK getId();
}
