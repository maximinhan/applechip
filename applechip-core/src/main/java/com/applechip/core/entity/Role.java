package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import com.applechip.core.abstact.GenericEntity;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "mt_role")
@EqualsAndHashCode(callSuper = false, of = { "id" })
@NoArgsConstructor
@Data
public class Role extends GenericEntity<String> implements GrantedAuthority {

	private static final long serialVersionUID = -5866977626393453234L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@Column(name = "authority", length = 20, unique = true)
	private String authority;
}
