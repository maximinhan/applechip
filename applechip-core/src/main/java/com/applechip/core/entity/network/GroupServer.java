package com.applechip.core.entity.network;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import com.applechip.core.AbstractObject;
import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;

@Entity
@Table(name = "nt_group_server")
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"group", "server"})
@NoArgsConstructor
@Data
public class GroupServer extends GenericUpdatedBy<GroupServer.Id> {
  private static final long serialVersionUID = -7378879232923520275L;
  @EmbeddedId
  private Id id;

  private int priority;

  @ManyToOne(optional = false)
  @JoinColumn(name = "group_id", insertable = false, updatable = false)
  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  private Group group;

  @ManyToOne(optional = false)
  @JoinColumn(name = "server_id", insertable = false, updatable = false)
  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  private Server server;

  public GroupServer(Group group, Server server) {
    this.group = group;
    this.server = server;
    this.id = new Id(group.getId(), server.getId());
    group.getGroupServers().add(this);
    server.getGroupServers().add(this);
  }

  public GroupServer(String groupId, String serverId, int priority) {
    this.id.groupId = groupId;
    this.id.serverId = serverId;
    this.priority = priority;
  }

  // public static class NetworkGroupServerComparator implements Comparator<NtGroupServer> {
  //
  // @Override
  // public int compare(NtGroupServer networkGroupServer1, NtGroupServer networkGroupServer2) {
  // if (networkGroupServer1.getServer() == null && networkGroupServer2.getServer() == null) {
  // return 0;
  // }
  // else if (networkGroupServer1.getServer() == null) {
  // return -1;
  // }
  // else if (networkGroupServer2.getServer() == null) {
  // return 1;
  // }
  //
  // if
  // (networkGroupServer1.getServer().getType().compareTo(networkGroupServer2.getServer().getType())
  // == 0) {
  // if (networkGroupServer1.getPriority() == networkGroupServer2.getPriority()) {
  // return 0;
  // }
  // else if (networkGroupServer1.getPriority() > networkGroupServer2.getPriority()) {
  // return 1;
  // }
  // else {
  // return -1;
  // }
  // }
  // else {
  // return
  // networkGroupServer1.getServer().getType().compareTo(networkGroupServer2.getServer().getType());
  // }
  // }
  // }

  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  @EqualsAndHashCode(callSuper = false, of = {"groupId", "serverId"})
  @Data
  public static class Id extends AbstractObject {

    private static final long serialVersionUID = 7431143630822624861L;

    @Column(name = "group_id", length = ColumnLengthConstant.UUID)
    private String groupId;

    @Column(name = "server_id", length = ColumnLengthConstant.UUID)
    private String serverId;
  }
}
