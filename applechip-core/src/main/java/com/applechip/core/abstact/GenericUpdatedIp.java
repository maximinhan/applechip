package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;

import lombok.Getter;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.SecurityUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
public abstract class GenericUpdatedIp<PK extends Serializable> extends
		GenericCreatedIp<PK> {

	@Column(name = "updated_ip", insertable = true, updatable = true, length = ColumnSizeConstant.UUID)
	protected String updatedIp;

	@PreUpdate
	private void setUpdatedBy() {
		this.updatedIp = SecurityUtil.getCurrentRemoteAddr();
	}
}
