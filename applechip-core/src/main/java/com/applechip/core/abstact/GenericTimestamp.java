package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@SuppressWarnings("serial")
@MappedSuperclass
// @EntityListeners({ AuditingEntityListener.class })
public abstract class GenericTimestamp<PK extends Serializable> extends GenericEntity<PK> {

  @Column(name = "updated_dt", insertable = true, updatable = true)
  // @CreatedDate
  private Date updatedDt;

  @Column(name = "created_dt", insertable = true, updatable = false)
  // @LastModifiedDate
  private Date createdDt;

  @PreUpdate
  public void setUpdatedDt() {
    this.updatedDt = new Date();
  }

  @PrePersist
  public void setCreatedDt() {
    Date date = new Date();
    this.createdDt = date;
    this.updatedDt = date;
  }

  public Date getCreatedDt() {
    return createdDt;
  }

  public Date getUpdatedDt() {
    return updatedDt;
  }

  public void setUpdatedDt(Date updatedDt) {
    this.updatedDt = updatedDt;
  }

  public void setCreatedDt(Date createdDt) {
    this.createdDt = createdDt;
  }
}
