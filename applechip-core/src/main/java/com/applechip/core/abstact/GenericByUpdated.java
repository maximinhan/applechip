package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.SecurityUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class GenericByUpdated<PK extends Serializable> extends GenericByCreated<PK> {

  @Column(name = "updated_by", insertable = true, updatable = true, length = ColumnSizeConstant.UUID)
  protected String updatedBy;

  @PreUpdate
  private void setUpdatedBy() {
    this.updatedBy = SecurityUtil.getCurrentUserId();
  }

  @PrePersist
  private void setCreatedBy() {
    String currentUserId = SecurityUtil.getCurrentUserId();
    this.createdBy = currentUserId;
    this.updatedBy = currentUserId;
  }
}
