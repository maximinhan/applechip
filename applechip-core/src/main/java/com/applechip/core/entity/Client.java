package com.applechip.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.ws.rs.FormParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

import com.applechip.core.abstact.GenericByUpdated;
import com.applechip.core.constant.ColumnSizeConstant;

@Entity
@Table(name = "mt_client")
@EqualsAndHashCode(callSuper = false, of = { "id" })
@ToString(exclude = { "user" })
@NoArgsConstructor
@Data
public class Client extends GenericByUpdated<String> {

	private static final long serialVersionUID = -2096664013765132476L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "_id", unique = true, length = ColumnSizeConstant.UUID)
	private String id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@FormParam("type")
	@Column(name = "type", length = 50)
	private String type;

	@FormParam("token")
	@Column(name = "token", length = 255)
	private String token;

}
