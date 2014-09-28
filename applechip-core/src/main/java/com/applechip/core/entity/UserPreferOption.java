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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.applechip.core.abstact.AbstractObject;
import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;

@Getter
@Setter
@SuppressWarnings(value = { "PMD.UnusedPrivateField", "PMD.SingularField" })
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "user", "optionCode" })
@NoArgsConstructor
@Entity
@Table(name = "mt_user_prefer_option")
@Audited
public class UserPreferOption extends GenericUpdatedBy<UserPreferOption.Id> {
	private static final long serialVersionUID = -6006862108744697176L;

	@EmbeddedId
	private Id id = new Id();

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private User user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "option_code_id", nullable = false, updatable = false, insertable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private OptionCode optionCode;

	@Column(name = "option_value", length = ColumnSizeConstant.OPTION_VALUE)
	private String optionValue;

	public UserPreferOption(User user, Integer optionCodeId, String optionValue) {
		this.user = user;
		this.id = new Id(user.getId(), optionCodeId);
		this.optionValue = optionValue;
	}

	public UserPreferOption(String userId, Integer optionCodeId, String optionValue) {
		this.id.userId = userId;
		this.id.optionCodeId = optionCodeId;
		this.optionValue = optionValue;
	}

	@EqualsAndHashCode(callSuper = false, of = { "userId", "optionCodeId" })
	@NoArgsConstructor
	@Embeddable
	@Data
	public static class Id extends AbstractObject {

		private static final long serialVersionUID = 6640163309533516196L;

		@Column(name = "user_id", length = ColumnSizeConstant.UUID)
		private String userId;

		@Column(name = "option_code_id")
		private Integer optionCodeId;

		public Id(String groupId, Integer optionCodeId) {
			this.userId = groupId;
			this.optionCodeId = optionCodeId;
		}

	}
}
