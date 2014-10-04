package com.applechip.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.util.SecurityUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
public abstract class GenericCreatedIp<PK extends Serializable> extends GenericUpdatedBy<PK> {
  @Column(name = "created_ip", insertable = true, updatable = false, length = ColumnLengthConstant.HOST)
  protected String createdIp;

  @PrePersist
  private void setCreatedIp() {
    this.createdIp = SecurityUtil.getCurrentRemoteAddr();
  }
}
