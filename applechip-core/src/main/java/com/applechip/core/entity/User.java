package com.applechip.core.entity;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.BitwisePermissions;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mt_user")
@EqualsAndHashCode(callSuper = false, of = { "username" })
@ToString(exclude = { "roles" })
@NoArgsConstructor
@Data
public class User extends GenericUpdatedBy<String> implements UserDetails {

	private static final long serialVersionUID = 1920694340054206260L;

	public static final long ACCOUNT_NON_EXPIRED = 1 << 0;

	public static final long ACCOUNT_NON_LOCKED = 1 << 1;

	public static final long CREDENTIALS_NON_EXPIRED = 1 << 2;

	public static final long ENABLED = 1 << 3;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@ManyToMany
	@JoinTable(name = "mt_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	private Set<Client> clients;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@NotAudited
	@JsonIgnore
	@MapKey(name = "id.optionCodeId")
	private Map<Integer, UserPreferOption> userPreferOptionMap;

	@Column(name = "username", length = ColumnSizeConstant.NAME, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = ColumnSizeConstant.PASSWORD, nullable = false)
	private String password;

	@Column(name = "status")
	private long status;

	@Column(name = "password_updated_dt")
	private Date passwordUpdatedDt;

	@Column(name = "password_initial", nullable = false)
	private boolean passwordInitial;

	@Column(name = "account_locked_dt")
	private Date accountLockedDt;

	@Column(name = "login_succeed_dt")
	@NotAudited
	private Date lastSucceedDt;

	@Column(name = "login_failed_count")
	@NotAudited
	private int loginFailcount;

	@Transient
	private String confirmPassword;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = new LinkedHashSet<GrantedAuthority>();
		grantedAuthorities.addAll(roles);
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return BitwisePermissions.isPermitted(this.status, ACCOUNT_NON_EXPIRED);
	}

	@Override
	public boolean isAccountNonLocked() {
		return BitwisePermissions.isPermitted(this.status, ACCOUNT_NON_LOCKED);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return BitwisePermissions.isPermitted(this.status, CREDENTIALS_NON_EXPIRED);
	}

	@Override
	public boolean isEnabled() {
		return BitwisePermissions.isPermitted(this.status, ENABLED);
	}

	@PrePersist
	private void prePersist() {

	}
}
