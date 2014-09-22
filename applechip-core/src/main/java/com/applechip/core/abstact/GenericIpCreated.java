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
public abstract class GenericIpCreated<PK extends Serializable> extends
		GenericByUpdated<PK> {

	@Column(name = "created_ip", insertable = true, updatable = false, length = ColumnSizeConstant.UUID)
	protected String createdIp;

	@PrePersist
	private void setCreatedIp() {
		this.createdIp = SecurityUtil.getCurrentRemoteAddr();
	}
}
