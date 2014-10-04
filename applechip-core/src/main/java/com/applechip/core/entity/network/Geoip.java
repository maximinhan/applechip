package com.applechip.core.entity.network;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericCreatedBy;

@Entity
@Table(name = "nt_geoip")
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"group"})
@NoArgsConstructor
@Data
public class Geoip extends GenericCreatedBy<String> {
  private static final long serialVersionUID = -8853302748562829399L;

  @Id
  @Column(name = "id", length = ColumnLengthConstant.CODE)
  private String id;

  @Column(name = "name", length = ColumnLengthConstant.NAME)
  private String name;

  @ManyToOne(optional = false)
  @JoinTable(name = "mt_geoip_group", joinColumns = {@JoinColumn(name = "geoip_id")}, inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Group group;
}
