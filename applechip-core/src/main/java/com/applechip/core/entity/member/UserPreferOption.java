package com.applechip.core.entity.member;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.applechip.core.AbstractObject;
import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;
import com.applechip.core.entity.code.Option;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"user", "option"})
@NoArgsConstructor
@Entity
@Table(name = "mt_user_prefer_option")
public class UserPreferOption extends GenericUpdatedBy<UserPreferOption.Id> {
  private static final long serialVersionUID = -6006862108744697176L;

  @EmbeddedId
  private Id id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "option_id", nullable = false, updatable = false, insertable = false)
  private Option option;

  @Column(name = "value", length = ColumnLengthConstant.VALUE)
  private String value;

  public UserPreferOption(User user, Option option, String value) {
    this.user = user;
    this.option = option;
    this.id = new Id(user.getId(), option.getId());
    this.value = value;
    user.getUserPreferOption().put(option.getId(), this);
  }

  @EqualsAndHashCode(callSuper = false, of = {"userId", "optionId"})
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  @Data
  public static class Id extends AbstractObject {
    private static final long serialVersionUID = 6640163309533516196L;
    @Column(name = "user_id", length = ColumnLengthConstant.UUID)
    private String userId;

    @Column(name = "option_id")
    private String optionId;

  }
}
