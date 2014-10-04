package com.applechip.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;

import com.applechip.core.util.DateUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
public abstract class GenericCreatedDt<PK extends Serializable> extends GenericEntity<PK> {
  @Column(name = "created_dt", insertable = true, updatable = false)
  protected Date createdDt;

  @PrePersist
  private void setCreatedDt() {
    this.createdDt = DateUtil.getDate();
  }
}
