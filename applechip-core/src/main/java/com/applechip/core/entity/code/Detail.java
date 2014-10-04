package com.applechip.core.entity.code;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.applechip.core.AbstractObject;
import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;

@Entity
@Table(name = "ct_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"category"})
@Data
public class Detail extends GenericUpdatedBy<Detail.Id> {
  private static final long serialVersionUID = -621758281942377622L;
  @EmbeddedId
  private Id id = new Id();

  @Column(name = "name", length = ColumnLengthConstant.NAME)
  private String name;

  @Column(name = "description", length = ColumnLengthConstant.DESCRIPTION)
  private String description;

  @Column(name = "enabled")
  private boolean enabled;

  @Column(name = "sequence")
  private int sequence;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
  @Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
  private Category category;

  @Embeddable
  @EqualsAndHashCode(callSuper = false, of = {"categoryId", "detailId"})
  @Data
  public static class Id extends AbstractObject {
    private static final long serialVersionUID = 5793297679481437367L;
    @Column(name = "category_id", length = ColumnLengthConstant.CODE)
    private String categoryId;

    @Column(name = "detail_id", length = ColumnLengthConstant.CODE)
    private String detailId;

    public String getId() {
      return String.format("%s.%s", categoryId, detailId);
    }
  }
}
