package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import lombok.Getter;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.SecurityUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
// @EntityListeners({ AuditingEntityListener.class })
public abstract class GenericUpdatedBy<PK extends Serializable> extends GenericCreatedBy<PK> {

  // @CreatedDate
  // @LastModifiedDate
  // @CreatedBy
  // @LastModifiedBy
  @Column(name = "updated_by", insertable = true, updatable = true, length = ColumnSizeConstant.UUID)
  protected String updatedBy;

  @PreUpdate
  private void setUpdatedBy() {
    this.updatedBy = SecurityUtil.getCurrentUserId();
  }
}