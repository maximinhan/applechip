package com.applechip.core.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import javax.annotation.PostConstruct;

import lombok.Getter;
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

  public GeoipLocation getGeoipLocation(String ipAddress) {
    CityResponse cityResponse = getCityResponse(ipAddress);
    GeoipLocation geoipLocation = GeoipLocation.newInstance(cityResponse);
    return geoipLocation;
  }

  public String getCountryCode(String ipAddress) {
    return getGeoipLocation(ipAddress).getCountryCode();
  }

  public String getTimezone(String ipAddress) {
    return getGeoipLocation(ipAddress).getTimezone();
  }

  private CityResponse getCityResponse(String ipAddress) {
    CityResponse cityResponse = null;
    try {
      cityResponse = databaseReader.city(InetAddress.getByName(ipAddress));
      databaseReader.close();
    } catch (Exception e) {
      log.debug(String.format("error. ipAddress:%s, errorMessage:%s", ipAddress, e.toString()));
    }
    return cityResponse;
  }

  @Getter
  @Setter
  public final static class GeoipLocation extends AbstractObject {

    private static final long serialVersionUID = 9025882291800096156L;

    private String countryCode;

    private String region;

    private String cityName;

    private String timezone;

    public static GeoipLocation newInstance(CityResponse cityResponse) {
      GeoipLocation geoipLocation = new GeoipLocation();
      if (cityResponse == null)
        return geoipLocation;

      geoipLocation.setCountryCode(cityResponse.getCountry().getIsoCode());
      geoipLocation.setRegion(cityResponse.getMostSpecificSubdivision().getIsoCode());
      geoipLocation.setTimezone(cityResponse.getLocation().getTimeZone());
      geoipLocation.setCityName(cityResponse.getCity().getName());
      return geoipLocation;
    }
  }
}
