package com.applechip.core.entity;

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

import com.applechip.core.abstact.GenericUpdatedIp;

@EqualsAndHashCode(callSuper = false, of = { "id" })
@Data
@Entity
@Table(name = "ht_revision")
@RevisionEntity(RevisionListener.class)
public class Revision extends GenericUpdatedIp<Integer> {

	private static final long serialVersionUID = -4087998864850401961L;

	@Id
	@GeneratedValue
	@RevisionNumber
	private Integer id;

	@RevisionTimestamp
	private Date date;

}