package com.applechip.core.entity;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.applechip.core.abstact.AbstractObject;
import com.applechip.core.abstact.GenericByUpdated;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "nt_network_group_server")
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "networkGroup", "server" })
@NoArgsConstructor
@Data
public class NetworkGroupServer extends GenericByUpdated<NetworkGroupServer.Id> {

	private static final long serialVersionUID = -7378879232923520275L;

	@EmbeddedId
	private Id id = new Id();

	@Audited
	private int priority;

	@ManyToOne(optional = false)
	@JoinColumn(name = "network_group_id", insertable = false, updatable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private NetworkGroup networkGroup;

	@ManyToOne(optional = false)
	@JoinColumn(name = "server_id", insertable = false, updatable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Server server;

	@Transient
	private int changedPriority;

	public NetworkGroupServer(NetworkGroup networkGroup, Server server) {
		this.networkGroup = networkGroup;
		this.server = server;

		this.id.networkGroupId = this.networkGroup.getId();
		this.id.serverId = this.server.getId();

		networkGroup.getNetworkGroupServers().add(this);
		server.getNetworkGroupServers().add(this);
	}

	public NetworkGroupServer(String networkGroupId, String serverId, int priority) {
		this.id.networkGroupId = networkGroupId;
		this.id.serverId = serverId;
		this.priority = priority;
	}

	public static class NetworkGroupServerComparator implements Comparator<NetworkGroupServer> {

		@Override
		public int compare(NetworkGroupServer networkGroupServer1, NetworkGroupServer networkGroupServer2) {
			if (networkGroupServer1.getServer() == null && networkGroupServer2.getServer() == null) {
				return 0;
			}
			else if (networkGroupServer1.getServer() == null) {
				return -1;
			}
			else if (networkGroupServer2.getServer() == null) {
				return 1;
			}

			if (networkGroupServer1.getServer().getType().compareTo(networkGroupServer2.getServer().getType()) == 0) {
				if (networkGroupServer1.getPriority() == networkGroupServer2.getPriority()) {
					return 0;
				}
				else if (networkGroupServer1.getPriority() > networkGroupServer2.getPriority()) {
					return 1;
				}
				else {
					return -1;
				}
			}
			else {
				return networkGroupServer1.getServer().getType().compareTo(networkGroupServer2.getServer().getType());
			}
		}
	}

	@NoArgsConstructor
	@Embeddable
	@EqualsAndHashCode(callSuper = false, of = { "networkGroupId", "serverId" })
	@Data
	public static class Id extends AbstractObject {

		private static final long serialVersionUID = 7431143630822624861L;

		@Column(name = "network_group_id", length = ColumnSizeConstant.UUID)
		private String networkGroupId;

		@Column(name = "server_id", length = ColumnSizeConstant.UUID)
		private String serverId;
	}
}