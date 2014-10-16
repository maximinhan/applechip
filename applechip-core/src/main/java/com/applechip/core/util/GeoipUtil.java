package com.applechip.core.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.applechip.core.AbstractObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

@Slf4j
public class GeoipUtil {
  private static DatabaseReader databaseReader;

  private GeoipUtil(String geoipFilePath) {
    if (databaseReader == null) {
      File database = new File(geoipFilePath);
      try {
        databaseReader = new DatabaseReader.Builder(database).build();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public GeoipLocation getGeoipLocation(String ip) {
    CityResponse cityResponse = null;
    try {
      cityResponse = databaseReader.city(InetAddress.getByName(ip));
      databaseReader.close();
    } catch (Exception e) {
      log.debug("error ip: {}, errorMessage: {}", ip, e.toString());
      return new GeoipLocation();
    }
    return new GeoipLocation(cityResponse);
  }

  public String getCountryCode(String ip) {
    return getGeoipLocation(ip).getCountryCode();
  }

  public String getLocationTimezone(String ip) {
    return getGeoipLocation(ip).getLocationTimezone();
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public final static class GeoipLocation extends AbstractObject {

    private static final long serialVersionUID = 9025882291800096156L;

    private String countryCode;

    private String mostSpecificSubdivisionCode;

    private String cityName;

    private String locationTimezone;

    public GeoipLocation(CityResponse cityResponse) {
      this.countryCode = cityResponse.getCountry().getIsoCode();
      this.mostSpecificSubdivisionCode = cityResponse.getMostSpecificSubdivision().getIsoCode();
      this.cityName = cityResponse.getCity().getName();
      this.locationTimezone = cityResponse.getLocation().getTimeZone();
    }
  }

  public static GeoipUtil getInstance(String geoipFilePath) {
    return new GeoipUtil(geoipFilePath);
  }
}
