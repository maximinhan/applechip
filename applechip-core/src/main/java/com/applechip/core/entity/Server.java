package com.applechip.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.applechip.core.abstact.GenericByUpdated;
import com.applechip.core.constant.ColumnSizeConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Audited
@Entity
@Table(name = "nt_server", uniqueConstraints = @UniqueConstraint(columnNames = { "type", "public_host" }))
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "networkGroupServers" })
@NoArgsConstructor
@Data
public class Server extends GenericByUpdated<String> {

	private static final long serialVersionUID = 774924486248120860L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@Column(name = "type", length = ColumnSizeConstant.CODE)
	@JsonProperty("type")
	private String type;

	@Column(name = "name", length = ColumnSizeConstant.NAME)
	@JsonProperty("name")
	private String name;

	@Column(name = "public_host", length = ColumnSizeConstant.HOST)
	@JsonProperty("publicHost")
	private String publicHost;

	@Column(name = "private_host", length = ColumnSizeConstant.HOST)
	@JsonProperty("privateHost")
	private String privateHost;

	@JsonProperty("port1")
	private int port1;

	@JsonProperty("port2")
	private int port2;

	@Column(name = "alive", nullable = false)
	@JsonProperty("alive")
	private boolean alive;

	@Transient
	private boolean oldAlive;

	@Column(name = "usable")
	@JsonProperty("usable")
	@NotAudited
	private Long usable;

	@Column(name = "enabled", nullable = false)
	@JsonProperty("enabled")
	private boolean enabled;

	@OneToMany(mappedBy = "server")
	@JsonIgnore
	@NotAudited
	private Set<NetworkGroupServer> networkGroupServers = new HashSet<NetworkGroupServer>();

	// @ManyToMany
	// @JoinTable(name = "nt_server_monitoring", joinColumns = { @JoinColumn(name = "server_id", referencedColumnName =
	// "server_id") })
	// @AuditJoinTable
	// @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	// @JsonIgnore
	// private Set<Server> serverMonitorings = new HashSet<Server>();

	@Transient
	private long useCount;

	public String getServerUrl(String serverAddress, boolean usessl) {
		String u = "";
		String p = "";
		if (usessl) {
			u = "https://";
			p = getPort2() == 443 ? "" : ":" + getPort2();
		}
		else {
			u = "http://";
			p = getPort1() == 80 ? "" : ":" + getPort1();
		}
		return u.concat(serverAddress).concat(p);
	}
}
