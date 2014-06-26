package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.applechip.core.constant.ColumnSizeConstant;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class GenericByUpdated<PK extends Serializable> extends GenericByCreated<PK> {

  @Column(name = "updated_by", insertable = true, updatable = true, length = ColumnSizeConstant.UUID)
  protected String updatedBy;

  @PreUpdate
  public void setUpdatedBy() {
    this.updatedBy = getCurrentUserId();
  }

  @PrePersist
  public void setCreatedBy() {
    String currentUserId = getCurrentUserId();
    this.createdBy = currentUserId;
    this.updatedBy = currentUserId;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }
}
