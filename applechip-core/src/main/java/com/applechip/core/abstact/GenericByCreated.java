package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.SecurityUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class GenericByCreated<PK extends Serializable> extends GenericDtUpdated<PK> {

  @Column(name = "created_by", insertable = true, updatable = false, length = ColumnSizeConstant.UUID)
  protected String createdBy;

  @PrePersist
  private void setCreatedBy() {
    String currentUserId = SecurityUtil.getCurrentUserId();
    this.createdBy = currentUserId;
  }
}
