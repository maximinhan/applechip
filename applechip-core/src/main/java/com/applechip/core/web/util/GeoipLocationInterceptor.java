package com.applechip.core.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.util.GeoipUtil;
import com.applechip.core.util.GeoipUtil.GeoipLocation;
import com.applechip.core.util.StringUtil;

@Slf4j
public class GeoipLocationInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  private GeoipUtil geoipUtil;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    GeoipLocation geoipLocation = (GeoipLocation) session.getAttribute(ApplicationConstant.ServerInfo.GEOIP_LOCATION);
    if (geoipLocation == null) {
      geoipLocation = geoipUtil.getGeoipLocation(request.getRemoteAddr());
      session.setAttribute(ApplicationConstant.ServerInfo.GEOIP_LOCATION, geoipLocation);
      log.trace("set {}={}", ApplicationConstant.ServerInfo.GEOIP_LOCATION, geoipLocation);
    }
    setupGeoipLocationNetworkGroup(session, geoipLocation);
    return super.preHandle(request, response, handler);
  }

  private void setupGeoipLocationNetworkGroup(HttpSession session, GeoipLocation geoipLocation) {
    @SuppressWarnings("unchecked")
    Map<String, String> geoipGroupMap = (Map<String, String>) session.getServletContext().getAttribute(ApplicationConstant.ServerInfo.GEOIP_GROUP_MAP);
    if (geoipGroupMap != null && geoipLocation != null) {
      String countryCode = StringUtil.lowerCase(geoipLocation.getCountryCode());
      if (StringUtil.isBlank(countryCode))
        return;
      String region = String.format("_%s", StringUtil.defaultString(StringUtil.lowerCase(geoipLocation.getSubdivisionCode()), "*"));
      String geoipId = geoipGroupMap.get(countryCode + region);
      if (StringUtil.isBlank(geoipId))
        geoipId = geoipGroupMap.get(countryCode + "_*");
      session.setAttribute(ApplicationConstant.ServerInfo.GEOIP_ID, geoipId);
    }
  }
}
