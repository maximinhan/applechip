package com.applechip.core.entity;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.ws.rs.FormParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.abstact.GenericTraceable;
import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.BitwisePermissions;

@Entity
@Table(name = "mt_user")
@EqualsAndHashCode(callSuper = false, of = {"username"})
@ToString(exclude = {"roles", "categories"})
@NoArgsConstructor
@Data
public class User extends GenericTraceable<String> implements UserDetails {

  private static final long serialVersionUID = 1920694340054206260L;

  public static final long ACCOUNT_NON_EXPIRED = 1 << 0;
  public static final long ACCOUNT_NON_LOCKED = 1 << 1;
  public static final long CREDENTIALS_NON_EXPIRED = 1 << 2;
  public static final long ENABLED = 1 << 3;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "id", length = ColumnSizeConstant.UUID)
  private String id;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "mt_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;// = Collections.emptySet();

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "mt_user_category", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
      "user_id", "category"}))
  @Column(name = "category", length = ColumnSizeConstant.UUID)
  private Set<String> categories;

  // @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional =
  // true)
  // @PrimaryKeyJoinColumn
  // @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, optional =
  // true)
  // @PrimaryKeyJoinColumn
  // private Client client;

  @FormParam("device")
  @Column(name = "device", length = 50)
  private String device;

  @FormParam("token")
  @Column(name = "token", length = 255)
  private String token;

  @FormParam("username")
  @Column(name = "username", length = 50, nullable = false, unique = true)
  private String username;

  @FormParam("password")
  @Column(name = "password", length = 50, nullable = false)
  private String password;

  @Column(name = "status")
  private long status;

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
    authorities.addAll(roles);
    return authorities;
  }

  @Transient
  public String getCategoryNames() {
    List<String> categoryList = new ArrayList<String>();
    if (this.categories != null && categories.size() > 0) {
      for (String category : categories) {
        categoryList.add(category);
      }
    }
    return StringUtils.join(categoryList.toArray(), ",");
  }

  public boolean isAccountNonExpired() {
    return BitwisePermissions.isPermitted(this.status, ACCOUNT_NON_EXPIRED);
  }

  public boolean isAccountNonLocked() {
    return BitwisePermissions.isPermitted(this.status, ACCOUNT_NON_LOCKED);
  }

  public boolean isCredentialsNonExpired() {
    return BitwisePermissions.isPermitted(this.status, CREDENTIALS_NON_EXPIRED);
  }

  public boolean isEnabled() {
    return BitwisePermissions.isPermitted(this.status, ENABLED);
  }
}
