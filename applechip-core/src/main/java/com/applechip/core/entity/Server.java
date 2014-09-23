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

	@JsonProperty("usable")
	private int usable;

	@Column(name = "alive", nullable = false)
	@JsonProperty("alive")
	private boolean alive;

	@Column(name = "enabled", nullable = false)
	@JsonProperty("enabled")
	private boolean enabled;

	@OneToMany(mappedBy = "server")
	@JsonIgnore
	@NotAudited
	private Set<NetworkGroupServer> networkGroupServers = new HashSet<NetworkGroupServer>();

	@OneToMany(mappedBy = "server")
	@JsonIgnore
	@NotAudited
	private Set<UploadFile> uploadFiles = new HashSet<UploadFile>();

	public String getUrl(String host, boolean usessl) {
		String schema = "";
		String port = "";
		if (usessl) {
			schema = "https://";
			port = this.port2 == 443 ? "" : String.format(":%s", this.port2);
		}
		else {
			schema = "http://";
			port = this.port1 == 80 ? "" : String.format(":%s", this.port1);
		}
		return String.format("%s%s%s", schema, host, port);
	}
}
