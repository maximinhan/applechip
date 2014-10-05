package com.applechip.core.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.applechip.core.entity.member.Client;
import com.applechip.core.entity.member.Location;
import com.applechip.core.entity.member.QClient;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.expr.MathExpressions;
import com.mysema.query.types.expr.NumberExpression;

@Repository
public class ClientRepositoryImpl extends GenericRepositoryImpl<Client, String> implements ClientRepository {
  public ClientRepositoryImpl() {
    super(Client.class);
  }

  // Location geoLocation = Location.fromDegrees(latitude, longitude);
  // double distance = 1;
  // Location[] locations = location.boundingCoordinates(distance,
  // Location.EarthRadius.KILOMETERS.value());
  // distance = distance / Location.EarthRadius.KILOMETERS.value();
  @Override
  public List<Client> getClientsByLatitudeAndLongitude(Location[] locations, Location location, double distance) {
    JPAQuery query = new JPAQuery(entityManager);
    QClient client = QClient.client;
    boolean meridian180WithinDistance = locations[0].getLongitudeInRadians() > locations[1].getLongitudeInRadians();
    query.from(client);
    query.where(client.latitude.goe(locations[0].getLatitudeInRadians()).and(client.latitude.loe(locations[1].getLatitudeInRadians())));
    if (meridian180WithinDistance) {
      query.where(client.longitude.goe(locations[0].getLongitudeInRadians()).or(client.longitude.loe(locations[1].getLongitudeInRadians())));
    } else {
      query.where(client.longitude.goe(locations[0].getLongitudeInRadians()).and(client.longitude.loe(locations[1].getLongitudeInRadians())));
    }
    NumberExpression<Double> acos =
        MathExpressions.acos(client.sinLatitude.multiply(Math.sin(location.getLatitudeInRadians())).add(
            client.cosLatitude.multiply(Math.cos(location.getLatitudeInRadians())).multiply(MathExpressions.cos(client.longitude.subtract(Expressions.constant(location.getLongitudeInRadians()))))));
    query.where(acos.loe(distance));
    return query.list(client);
  }
}
