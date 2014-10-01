package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.applechip.core.abstact.AbstractObject;
import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "ct_code_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings(value = { "PMD.UnusedPrivateField", "PMD.SingularField" })
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString
@Data
public class CodeDetail extends GenericUpdatedBy<CodeDetail.Id> {

	private static final long serialVersionUID = 972718107394781677L;

	@EmbeddedId
	private Id id = new Id();

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "code_category_id", nullable = false, referencedColumnName = "code_category_id", insertable = false, updatable = false),
			@JoinColumn(name = "code_id", nullable = false, referencedColumnName = "code_id", insertable = false, updatable = false) })
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Code code;

	@Column(name = "name", length = ColumnSizeConstant.NAME)
	private String name;

	@Column(name = "description", length = ColumnSizeConstant.DESCRIPTION)
	private String description;

	@Column(name = "sequence")
	private int sequence;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Embeddable
	@EqualsAndHashCode(callSuper = false, of = { "codeCategoryId", "codeId", "codeDetailId" })
	@Data
	public static class Id extends AbstractObject {

		private static final long serialVersionUID = -931491241598487349L;

		@Column(name = "code_category_id", length = ColumnSizeConstant.CODE)
		private String codeCategoryId;

		@Column(name = "code_id", length = ColumnSizeConstant.CODE)
		private String codeId;

		@Column(name = "code_detail_id", length = ColumnSizeConstant.CODE)
		private String codeDetailId;

		public String getId() {
			return String.format("%s.%s.%s", codeCategoryId, codeId, codeDetailId);
		}

	}

}