package com.applechip.core.entity;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.FormParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.applechip.core.abstact.GenericByUpdated;
import com.applechip.core.constant.ColumnSizeConstant;
import com.applechip.core.util.BitwisePermissions;

@Entity
@Table(name = "mt_user")
@EqualsAndHashCode(callSuper = false, of = { "username" })
@ToString(exclude = { "roles" })
@NoArgsConstructor
@Data
public class User extends GenericByUpdated<String> implements UserDetails {

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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "mt_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;// = Collections.emptySet();

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

	@Column(name = "password_update_dt")
	private Date passwordUpdateDt;

	@Column(name = "initial_password_yn", nullable = false)
	private boolean initialPassword;

	@Column(name = "account_lock_dt")
	private Date accountLockDt;

	@Column(name = "last_login_dt")
	@NotAudited
	private Date lastLoginDt;

	@Column(name = "login_fail_count")
	@NotAudited
	private Integer loginFailcount;

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
