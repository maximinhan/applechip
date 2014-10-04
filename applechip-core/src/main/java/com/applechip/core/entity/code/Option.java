package com.applechip.core.entity.code;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;
import com.applechip.core.entity.member.UserPreferOption;

@Entity
@Table(name = "ct_option")
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"category", "userPreferOptions"})
@NoArgsConstructor
@Data
public class Option extends GenericUpdatedBy<String> {
  private static final long serialVersionUID = -1514733598944448583L;
  @Id
  @Column(name = "id", length = ColumnLengthConstant.CODE)
  private String id;

  @Column(name = "name", length = ColumnLengthConstant.NAME)
  private String name;

  @Column(name = "description", length = ColumnLengthConstant.DESCRIPTION)
  private String description;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @Column(name = "option_for")
  private long optionFor;

//  @Column(name = "section")
//  private long section;

  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "option")
  private Set<UserPreferOption> userPreferOptions;
}
