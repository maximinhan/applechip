package com.applechip.core.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.applechip.core.AbstractObject;
import com.applechip.core.properties.ApplicationProperties;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

@Component
@Slf4j
public class GeoipUtil {
  @Autowired
  private ApplicationProperties applicationProperties;

  private DatabaseReader databaseReader;

  public GeoipUtil() {}

  @PostConstruct
  public void init() {
    String geoipFilePath = applicationProperties.getGeoipFilePath();
    File database = new File(geoipFilePath);
    try {
      databaseReader = new DatabaseReader.Builder(database).build();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public GeoipLocation getGeoipLocation(String host) {
    CityResponse cityResponse = null;
    try {
      cityResponse = databaseReader.city(InetAddress.getByName(host));
      databaseReader.close();
    } catch (Exception e) {
      log.debug("error. host: {}, errorMessage: {}", host, e.toString());
      return new GeoipLocation();
    }
    return new GeoipLocation(cityResponse.getCountry().getIsoCode(), cityResponse.getMostSpecificSubdivision().getIsoCode(), cityResponse.getCity().getName(), cityResponse.getLocation().getTimeZone());
  }

  public String getCountryCode(String host) {
    return getGeoipLocation(host).getCountryCode();
  }

  public String getLocationTimezone(String host) {
    return getGeoipLocation(host).getLocationTimezone();
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public final static class GeoipLocation extends AbstractObject {

    private static final long serialVersionUID = 9025882291800096156L;

    private String countryCode;

    private String subdivisionCode;

    private String cityName;

    private String locationTimezone;

    public GeoipLocation(String countryCode, String subdivisionCode, String cityName, String locationTimezone) {
      super();
      this.countryCode = countryCode;
      this.subdivisionCode = subdivisionCode;
      this.cityName = cityName;
      this.locationTimezone = locationTimezone;
    }
  }
}
