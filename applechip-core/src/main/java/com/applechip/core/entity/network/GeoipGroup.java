package com.applechip.core.entity.network;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.AbstractEntity;
import com.applechip.core.entity.GenericCreatedBy;

@Data
@Entity
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"geoip", "group"})
@Table(name = "mt_geoip_group")
public class GeoipGroup extends GenericCreatedBy<GeoipGroup.Id> {
  private static final long serialVersionUID = 2550828957954692492L;

  @EmbeddedId
  private Id id = new Id();

  @ManyToOne(optional = false)
  @JoinColumn(name = "geoip_id", insertable = false, updatable = false)
  private Geoip geoip;

  @ManyToOne(optional = false)
  @JoinColumn(name = "group_id", insertable = false, updatable = false)
  private Group group;

  @Embeddable
  @EqualsAndHashCode(callSuper = false, of = {"geoipId", "region"})
  @Data
  public static class Id extends AbstractEntity {
    private static final long serialVersionUID = -1717445322232088831L;

    @Column(name = "geoip_id", length = ColumnLengthConstant.CODE)
    private String geoipId;

    @Column(name = "region", length = ColumnLengthConstant.CODE)
    private String region;
  }
}
