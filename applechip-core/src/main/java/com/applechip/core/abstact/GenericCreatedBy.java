package com.applechip.core.abstact;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import lombok.Getter;

import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.SecurityUtil;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
public abstract class GenericCreatedBy<PK extends Serializable> extends
		GenericUpdatedDt<PK> {

	@Column(name = "created_by", insertable = true, updatable = false, length = ColumnSizeConstant.UUID)
	protected String createdBy;

	@PrePersist
	private void setCreatedBy() {
		this.createdBy = SecurityUtil.getCurrentUserId();
	}
}
