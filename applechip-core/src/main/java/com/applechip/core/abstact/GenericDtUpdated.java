package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class GenericDtUpdated<PK extends Serializable> extends GenericDtCreated<PK> {

  @Column(name = "updated_dt", insertable = true, updatable = true)
  protected Date updatedDt;

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
}
