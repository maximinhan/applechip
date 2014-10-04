package com.applechip.core.entity.network;

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

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;
import com.applechip.core.entity.etc.UploadFile;

@Audited
@Entity
@Table(name = "nt_server", uniqueConstraints = @UniqueConstraint(columnNames = {"type", "public_host"}))
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"groupServers"})
@NoArgsConstructor
@Data
public class Server extends GenericUpdatedBy<String> {
  private static final long serialVersionUID = 774924486248120860L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", length = ColumnLengthConstant.UUID)
  private String id;

  @Column(name = "type", length = ColumnLengthConstant.CODE)
  private String type;

  @Column(name = "name", length = ColumnLengthConstant.NAME)
  private String name;

  @Column(name = "public_host", length = ColumnLengthConstant.HOST)
  private String publicHost;

  @Column(name = "private_host", length = ColumnLengthConstant.HOST)
  private String privateHost;

  private int port1;

  private int port2;

  private int usable;

  @Column(name = "alive", nullable = false)
  private boolean alive;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @OneToMany(mappedBy = "server")
  @NotAudited
  private Set<GroupServer> groupServers;

  @OneToMany(mappedBy = "server")
  @NotAudited
  private Set<UploadFile> uploadFiles;

  public String getUrl(String host, boolean usessl) {
    String schema = "";
    String port = "";
    if (usessl) {
      schema = "https://";
      port = this.port2 == 443 ? "" : String.format(":%s", this.port2);
    } else {
      schema = "http://";
      port = this.port1 == 80 ? "" : String.format(":%s", this.port1);
    }
    return String.format("%s%s%s", schema, host, port);
  }
}
