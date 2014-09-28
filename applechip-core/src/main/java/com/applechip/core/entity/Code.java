package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.applechip.core.abstact.AbstractObject;
import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "ct_code")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings(value = { "PMD.UnusedPrivateField", "PMD.SingularField" })
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "codeCategory" })
@Data
public class Code extends GenericUpdatedBy<Code.Id> {

	private static final long serialVersionUID = -621758281942377622L;

	@EmbeddedId
	private Id id = new Id();

	@ManyToOne
	@JoinColumn(name = "code_category_id", nullable = false, referencedColumnName = "_id", insertable = false, updatable = false)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private CodeCategory codeCategory;

	@Column(name = "sequence")
	private int sequence;

	@Column(name = "name", length = ColumnSizeConstant.NAME)
	private String name;

	@Column(name = "description", length = ColumnSizeConstant.DESCRIPTION)
	private String description;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Embeddable
	@EqualsAndHashCode(callSuper = false, of = { "codeCategoryId", "codeId" })
	@Data
	public static class Id extends AbstractObject {

		private static final long serialVersionUID = 5793297679481437367L;

		@Column(name = "code_category_id", length = ColumnSizeConstant.CODE)
		private String codeCategoryId;

		@Column(name = "code_id", length = ColumnSizeConstant.CODE)
		private String codeId;

	}

}