package com.applechip.core.entity.code;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ct_category")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@EqualsAndHashCode(callSuper = false, of = {"id"})
@Data
public class Category extends GenericUpdatedBy<String> {
  private static final long serialVersionUID = -3166875807686855167L;
  @Id
  @Column(name = "id", length = ColumnLengthConstant.CODE)
  private String id;

  @Column(name = "name", length = ColumnLengthConstant.NAME)
  private String name;

  @Column(name = "description", length = ColumnLengthConstant.DESCRIPTION)
  private String description;

  @Column(name = "enabled")
  private boolean enabled;

  @OneToMany(mappedBy = "category")
  @JsonIgnore
  private Set<Option> options = new HashSet<Option>();
}
