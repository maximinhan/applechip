package com.applechip.core.entity.member;

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

import com.applechip.core.constant.ColumnLengthConstant;
import com.applechip.core.entity.GenericUpdatedBy;

@Entity
@Table(name = "mt_client")
@EqualsAndHashCode(callSuper = false, of = {"id"})
@ToString(exclude = {"user"})
@NoArgsConstructor
@Data
public class Client extends GenericUpdatedBy<String> {

  private static final long serialVersionUID = -2096664013765132476L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(name = "id", unique = true, length = ColumnLengthConstant.UUID)
  private String id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "latitude")
  private double latitude;

  @Column(name = "longitude")
  private double longitude;

  @Column(name = "sin_latitude")
  private double sinLatitude;

  @Column(name = "cos_latitude")
  private double cosLatitude;

  @Column(name = "type", length = 50)
  private String type;

  @Column(name = "token", length = 255)
  private String token;

  @Column(name = "status")
  private long status;

  public double getLongitude() {
    return Math.toDegrees(this.longitude);
  }

  public void setLongitude(double longitude) {
    this.longitude = Math.toRadians(longitude);
  }

  public double getLatitude() {
    return Math.toDegrees(this.latitude);
  }

  public void setLatitude(double latitude) {
    this.latitude = Math.toRadians(latitude);
    this.cosLatitude = Math.cos(this.latitude);
    this.sinLatitude = Math.sin(this.latitude);
  }
}
