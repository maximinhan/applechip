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

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.abstact.GenericTraceable;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "mt_user")
public class User extends GenericTraceable<String> implements UserDetails {

  private static final long serialVersionUID = 1920694340054206260L;

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
      "user_id", "category" }))
  @Column(name = "category", length = ColumnSizeConstant.UUID)
  private Set<String> categories;

  // @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional =
  // true)
  // @PrimaryKeyJoinColumn
//  @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, optional = true)
//  @PrimaryKeyJoinColumn
//  private Client client;

//  @FormParam("device")
  @Column(name = "device", length = 50)
  private String device;

//  @FormParam("token")
  @Column(name = "token", length = 255)
  private String token;

//  @FormParam("username")
  @Column(name = "username", length = 50, nullable = false, unique = true)
  private String username;

//  @FormParam("password")
  @Column(name = "password", length = 50, nullable = false)
  private String password;

  @Column(name = "account_non_expired", nullable = false)
  private boolean accountNonExpired;

  @Column(name = "account_non_locked", nullable = false)
  private boolean accountNonLocked;

  @Column(name = "credentials_non_expired", nullable = false)
  private boolean credentialsNonExpired;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  // public List<String> getRoleAuthorityList() {
  // List<String> list = new ArrayList<String>();
  // if (!CollectionUtils.isEmpty(roles)) {
  // for (Role role : roles) {
  // list.add(role.getAuthority().toLowerCase());
  // }
  // }
  // return list;
  // }

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<String> getCategories() {
    return categories;
  }

  public void setCategories(Set<String> categories) {
    this.categories = categories;
  }

//  public Client getClient() {
//    return client;
//  }
//
//  public void setClient(Client client) {
//    this.client = client;
//  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    this.accountNonExpired = accountNonExpired;
  }

  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    this.accountNonLocked = accountNonLocked;
  }

  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    this.credentialsNonExpired = credentialsNonExpired;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", device=" + device + ", token=" + token + ", username=" + username + ", password=" + password
        + ", accountNonExpired=" + accountNonExpired + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired
        + ", enabled=" + enabled + "]";
  }
}