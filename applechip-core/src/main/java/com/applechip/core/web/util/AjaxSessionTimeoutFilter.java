package com.applechip.core.web.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

//<security:http>
//<!-- check session time out with ajax -->
//<security:custom-filter after="EXCEPTION_TRANSLATION_FILTER" ref="ajaxSessionTimeoutFilter"/>
//</security:http>
//
//<bean id="ajaxSessionTimeoutFilter" class="com.kyu.svc.common.filter.AjaxSessionTimeoutFilter">
//<property name="ajaxHeader" value="AJAX" />
//</bean>
public class AjaxSessionTimeoutFilter implements javax.servlet.Filter {

	private String ajaxHeader;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (isAjaxRequest(req)) {
			try {
				chain.doFilter(req, res);
			}
			catch (AccessDeniedException e) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			catch (AuthenticationException e) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		else {
			chain.doFilter(req, res);
		}
	}

	private boolean isAjaxRequest(HttpServletRequest req) {
		return req.getHeader(ajaxHeader) != null && req.getHeader(ajaxHeader).equals(Boolean.TRUE.toString());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
/*
 * jsp $('#start').click(function() { $.ajax({ type : "GET", url : "${contextPath}/test/progressbar.json", dataType :
 * "json", success : function(response) { alert(response.result); } }); });
 */
/*
 * jquery-common.js (function($) {
 * 
 * $.ajaxSetup({ beforeSend: function(xhr) { xhr.setRequestHeader("AJAX", true); }, error: function(xhr, status, err) {
 * if (xhr.status == 401) { alert("401"); } else if (xhr.status == 403) { alert("403"); } else {
 * alert("예외가 발생했습니다. 관리자에게 문의하세요."); } } });
 * 
 * })(jQuery);
 */
