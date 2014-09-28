package com.applechip.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.envers.NotAudited;

import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ct_option_code")
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString
@NoArgsConstructor
@Data
public class OptionCode extends GenericUpdatedBy<Integer> {

	private static final long serialVersionUID = -1514733598944448583L;

	@Id
	@Column(name = "option_code_id")
	private Integer id;

	@Column(name = "code_name", length = ColumnSizeConstant.NAME)
	private String codeName;

	@Column(name = "option_for")
	private long optionFor;

	@Column(name = "option_section")
	private int section;

	@Column(name = "category_code", length = ColumnSizeConstant.CODE)
	private String categoryCode;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "description", length = ColumnSizeConstant.DESCRIPTION)
	private String description;

	@OneToMany(mappedBy = "optionCode")
	@JsonIgnore
	@NotAudited
	private Set<UserPreferOption> userPreferOptions = new HashSet<UserPreferOption>();

}
