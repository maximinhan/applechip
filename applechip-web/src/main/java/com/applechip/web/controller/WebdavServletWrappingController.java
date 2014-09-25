package com.applechip.web.controller;

import org.springframework.web.servlet.mvc.ServletWrappingController;

public class WebdavServletWrappingController extends ServletWrappingController {

	public WebdavServletWrappingController() {
		String[] supportedMethods = { "OPTIONS", "GET", "HEAD", "POST", "TRACE", "PROPFIND", "PROPPATCH", "MKCOL",
				"COPY", "PUT", "DELETE", "MOVE", "LOCK", "UNLOCK", "VERSION-CONTROL" };
		this.setSupportedMethods(supportedMethods);
	}
}

// <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
// <init-param>
// <param-name>dispatchOptionsRequest</param-name>
// <param-value>true</param-value>
// </init-param>