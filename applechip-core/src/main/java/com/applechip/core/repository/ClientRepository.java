package com.applechip.core.repository;

import java.util.List;

import com.applechip.core.entity.member.Client;
import com.applechip.core.entity.member.Location;

public interface ClientRepository extends GenericRepository<Client, String> {

  List<Client> getClientsByLatitudeAndLongitude(Location[] locations, Location location, double distance);
}
