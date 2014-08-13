package com.applechip.core.abstact;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class GenericDtCreated<PK extends Serializable> extends GenericEntity<PK> {

  @Column(name = "created_dt", insertable = true, updatable = false)
  protected Date createdDt;

  @PrePersist
  private void setCreatedDt() {
    Date date = new Date();
    this.createdDt = date;
  }
}
