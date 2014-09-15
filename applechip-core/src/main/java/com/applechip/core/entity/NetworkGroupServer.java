package com.applechip.core.entity;

import java.io.Serializable;
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
	private Integer priority;

	@ManyToOne(optional = false)
	@JoinColumn(name = "network_group_id", insertable = false, updatable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private NetworkGroup networkGroup;

	@ManyToOne(optional = false)
	@JoinColumn(name = "server_id", insertable = false, updatable = false)
	@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
	private Server server;

	@Transient
	private Integer changedPriority; // 변경순서 저장 파라메터

	public NetworkGroupServer(NetworkGroup networkGroup, Server server) {
		// 필드 설정
		this.networkGroup = networkGroup;
		this.server = server;

		// 식별자 값 설정
		this.id.networkGroupId = networkGroup.getId();
		this.id.serverId = server.getId();

		// 참조 무결성
		networkGroup.getNetworkGroupServers().add(this);
		server.getNetworkGroupServers().add(this);
	}

	public NetworkGroupServer(String networkGroupId, String serverId, Integer priority) {
		this.id.networkGroupId = networkGroupId;
		this.id.serverId = serverId;
		this.priority = priority;
	}

	public static class NetworkGroupServerComparator implements Comparator<NetworkGroupServer>, Serializable {

		private static final long serialVersionUID = -3529968288458173958L;

		@Override
		public int compare(NetworkGroupServer netServer1, NetworkGroupServer netServer2) {
			if (netServer1.getServer() == null && netServer2.getServer() == null) {
				return 0;
			}
			else if (netServer1.getServer() == null) {
				return -1;
			}
			else if (netServer2.getServer() == null) {
				return 1;
			}

			if (netServer1.getServer().getType().compareTo(netServer2.getServer().getType()) == 0) {
				if (netServer1.getPriority() == null && netServer2.getPriority() == null) {
					return 0;
				}
				else if (netServer1.getPriority() == null) {
					return -1;
				}
				else if (netServer2.getPriority() == null) {
					return 1;
				}

				if (netServer1.getPriority().equals(netServer2.getPriority())) {
					return netServer1.getServer().getUsable().compareTo(netServer2.getServer().getUsable());
				}
				else if (netServer1.getPriority() > netServer2.getPriority()) {
					return 1;
				}
				else {
					return -1;
				}
			}
			else {
				return netServer1.getServer().getType().compareTo(netServer2.getServer().getType());
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