package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class GenericDtCreated<PK extends Serializable> extends GenericEntity<PK> {

  @Column(name = "created_dt", insertable = true, updatable = false)
  protected Date createdDt;

  @PrePersist
  public void setCreatedDt() {
    Date date = new Date();
    this.createdDt = date;
  }

  public Date getCreatedDt() {
    return createdDt;
  }

  public void setCreatedDt(Date createdDt) {
    this.createdDt = createdDt;
  }
}
