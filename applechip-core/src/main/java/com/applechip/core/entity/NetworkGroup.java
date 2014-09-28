package com.applechip.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Audited
@Entity
@Table(name = "nt_network_group")
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "networkGroupServers" })
@NoArgsConstructor
@Data
public class NetworkGroup extends GenericUpdatedBy<String> {

	private static final long serialVersionUID = -1515057292914459397L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@Column(name = "name", length = ColumnSizeConstant.NAME)
	private String name;

	@OneToMany(targetEntity = NetworkGroupServer.class, cascade = CascadeType.ALL, mappedBy = "networkGroup", orphanRemoval = true)
	@OrderBy("priority")
	@JsonIgnore
	@NotAudited
	private Set<NetworkGroupServer> networkGroupServers = new HashSet<NetworkGroupServer>();

}