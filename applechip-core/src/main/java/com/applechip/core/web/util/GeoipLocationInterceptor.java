package com.applechip.core.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.applechip.core.util.GeoipUtil;

@Slf4j
public class GeoipLocationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private GeoipUtil geoipUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// if (log.isTraceEnabled())
		// log.trace("start...");
		// HttpSession session = request.getSession();
		// GeoipLocation geoipLocation = (GeoipLocation) session.getAttribute(BaseConstants.GEOIP_LOCATION);
		// if (geoipLocation == null) {
		// String remoteAddr = request.getRemoteAddr();
		// geoipLocation = geoipUtil.getGeoipLocation(remoteAddr);
		// session.setAttribute(BaseConstants.GEOIP_LOCATION, geoipLocation);
		// if (log.isTraceEnabled())
		// log.trace(String.format("set %s=%s", BaseConstants.GEOIP_LOCATION, geoipLocation));
		// }
		// setupGeoipLocationNetworkGroup(session, geoipLocation);
		return super.preHandle(request, response, handler);
	}

	// private void setupGeoipLocationNetworkGroup(HttpSession session, GeoipLocation geoipLocation) {
	// @SuppressWarnings("unchecked")
	// Map<String, String> countryNeworkGroupMap = (Map<String, String>) session.getServletContext().getAttribute(
	// BaseConstants.COUNTRY_NETWORK_GROUP_MAP);
	// if (countryNeworkGroupMap != null && geoipLocation != null) {
	// if (StringUtil.isBlank(geoipLocation.getCountryCode()))
	// return;
	// String countryCode = StringUtil.lowerCase(geoipLocation.getCountryCode());
	// if (StringUtil.isBlank(countryCode))
	// return;
	// String region = StringUtil.lowerCase(geoipLocation.getRegion());
	// region = StringUtil.defaultString(region, "*");
	// String key = countryCode + "_" + region;
	// String networkgroupId = countryNeworkGroupMap.get(key);
	// if (StringUtil.isBlank(networkgroupId))
	// networkgroupId = countryNeworkGroupMap.get(countryCode + "_*");
	// session.setAttribute(BaseConstants.GEOIP_LOCATION_NETWORK_GROUP_ID, networkgroupId);
	// }
	// }
}