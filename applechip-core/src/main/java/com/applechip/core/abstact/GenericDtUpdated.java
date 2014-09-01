package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import lombok.Getter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
public abstract class GenericDtUpdated<PK extends Serializable> extends GenericDtCreated<PK> {

  @Column(name = "updated_dt", insertable = true, updatable = true)
  protected Date updatedDt;

  @PreUpdate
  private void setUpdatedDt() {
    this.updatedDt = new Date();
  }
}
