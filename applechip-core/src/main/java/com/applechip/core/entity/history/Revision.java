package com.applechip.core.entity.history;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.applechip.core.entity.GenericUpdatedIp;

@EqualsAndHashCode(callSuper = false, of = {"id"})
@Data
@Entity
@Table(name = "ht_revision")
@RevisionEntity(RevisionListener.class)
public class Revision extends GenericUpdatedIp<Long> {
  private static final long serialVersionUID = -4087998864850401961L;
  @Id
  @GeneratedValue
  @RevisionNumber
  private Long id;

  @RevisionTimestamp
  private Date date;
}
