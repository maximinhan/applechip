package com.applechip.core.entity.network;

import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedSet;

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
import org.hibernate.annotations.SortComparator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;

@Audited
@Entity
@Table(name = "nt_group")
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"groupServers"})
@NoArgsConstructor
@Data
public class Group extends GenericUpdatedBy<String> {
  private static final long serialVersionUID = -1515057292914459397L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", unique = true, length = ColumnLengthConstant.UUID)
  private String id;

  @Column(name = "name", length = ColumnLengthConstant.NAME)
  private String name;

  @OneToMany(targetEntity = GroupServer.class, cascade = CascadeType.ALL, mappedBy = "group", orphanRemoval = true)
  @OrderBy("priority")
  @NotAudited
  @SortComparator(value = GroupServerComparator.class)
  private SortedSet<GroupServer> groupServers;

  private static class GroupServerComparator implements Comparator<GroupServer>, Serializable {
    private static final long serialVersionUID = 5548763152116249874L;

    @Override
    public int compare(GroupServer groupServer1, GroupServer groupServer2) {
      if (groupServer1.getServer() == null && groupServer2.getServer() == null) {
        return 0;
      } else if (groupServer1.getServer() == null) {
        return -1;
      } else if (groupServer2.getServer() == null) {
        return 1;
      }
      if (groupServer1.getServer().getType().compareTo(groupServer2.getServer().getType()) == 0) {
        if (groupServer1.getPriority() == groupServer2.getPriority()) {
          return 0;
        } else if (groupServer1.getPriority() > groupServer2.getPriority()) {
          return 1;
        } else {
          return -1;
        }
      } else {
        return groupServer1.getServer().getType().compareTo(groupServer2.getServer().getType());
      }
    }
  }
}
