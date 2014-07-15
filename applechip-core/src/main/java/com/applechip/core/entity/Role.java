package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import com.applechip.core.abstact.GenericEntity;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "mt_role")
@EqualsAndHashCode(callSuper = false, of = {"id"})
public class Role extends GenericEntity<String> implements GrantedAuthority {

  private static final long serialVersionUID = -5866977626393453234L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "id", length = ColumnSizeConstant.UUID)
  private String id;

  @Column(name = "authority", length = 20, unique = true)
  private String authority;

  @Override
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return "Role [id=" + id + ", authority=" + authority + "]";
  }
  
  
}
