package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

import com.applechip.core.abstact.GenericUpdatedBy;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "mt_client")
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "user" })
@NoArgsConstructor
@Data
public class Client extends GenericUpdatedBy<String> {

	private static final long serialVersionUID = -2096664013765132476L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "type", length = 50)
	private String type;

	@Column(name = "token", length = 255)
	private String token;

}
