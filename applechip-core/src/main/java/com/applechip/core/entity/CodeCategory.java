package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "ct_code_category")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings(value = { "PMD.UnusedPrivateField", "PMD.SingularField" })
@EqualsAndHashCode(callSuper = false, of = { "id" })
@Data
public class CodeCategory extends GenericUpdatedBy<String> {

	private static final long serialVersionUID = -3166875807686855167L;

	@Id
	@Column(name = "_id", length = ColumnSizeConstant.CODE)
	private String id;

	@Column(name = "name", length = ColumnSizeConstant.NAME)
	private String name;

	@Column(name = "description", length = ColumnSizeConstant.DESCRIPTION)
	private String description;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

}
