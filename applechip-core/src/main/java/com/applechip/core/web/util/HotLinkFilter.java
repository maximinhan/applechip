package com.applechip.core.web.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/*
 * using
 * <filter>
 <filter-name>Hotlink-Filter</filter-name>
 <filter-class>com.yourcompany.HotLinkFilter</filter-class>
 <init-param>
 <param-name>pattern1</param-name>
 <param-value>http://.*\.mysite.com/.*\.(jpe?g|gif|png) 
 http://.*\.mysite.com/.*</param-value>
 </init-param>
 </filter>

 apache
 RewriteEngine on 
 RewriteCond %{HTTP_REFERER} . 
 RewriteCond %{HTTP_REFERER} !^http://(www\\.)?yoursite\\.com [NC] 
 RewriteRule \\.(gif|jpe?g)$ /images/hotlink.$1 [L]

 xml
 */
// <rule>
//    <name>Blocked Inline-Images</name>
//    <note>
//        Assume we have under http://www.quux-corp.de/~quux/ some pages with inlined GIF graphics. These graphics are
//        nice, so others directly incorporate them via hyperlinks to their pages. We don't like this practice because
//        it adds useless traffic to our server.
//
//        While we cannot 100% protect the images from inclusion, we can at least restrict the cases where the browser
//        sends a HTTP Referer header.
//
//        RewriteCond %{HTTP_REFERER} !^$
//        RewriteCond %{HTTP_REFERER} !^http://www.quux-corp.de/~quux/.*$ [NC]
//        RewriteRule .*\.gif$ - [F]
//    </note>
//    <condition name="referer" operator="notequal">^$</condition>
//    <condition name="referer" operator="notequal">^http://www.quux-corp.de/~quux/.*$</condition>
//    <from>.*\.gif$</from>
//    <set type="status">403</set>
//    <to>null</to>
//</rule>
//
//<rule>
//    <name>Blocked Inline-Images example 2</name>
//    <note>
//        RewriteCond %{HTTP_REFERER} !^$
//        RewriteCond %{HTTP_REFERER} !.*/foo-with-gif\.html$
//        RewriteRule ^inlined-in-foo\.gif$ - [F]
//    </note>
//    <condition name="referer" operator="notequal">^$</condition>
//    <condition name="referer" operator="notequal">.*/foo-with-gif\.html$</condition>
//    <from>^inlined-in-foo\.gif$</from>
//    <set type="status">403</set>
//    <to>null</to>
//</rule>
public class HotLinkFilter implements Filter {

	private final Map<Pattern, Pattern> PATTERNS = new ConcurrentHashMap<Pattern, Pattern>();

	private void addPatterns(final String targetPattern, final String referrerPattern) {
		PATTERNS.put(Pattern.compile(targetPattern), Pattern.compile(referrerPattern));
	}

	@Override
	public void init(final FilterConfig config) throws ServletException {
		@SuppressWarnings("unchecked")
		final Enumeration<String> parameterNames = config.getInitParameterNames();
		while (parameterNames.hasMoreElements()) {
			final String nextParam = parameterNames.nextElement();
			if (nextParam.startsWith("pattern")) {
				final String[] patterns = config.getInitParameter(nextParam).split("\\s+");
				if (patterns.length == 2) {
					addPatterns(patterns[0], patterns[1]);
				}
			}
		}
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			final HttpServletRequest hsr = (HttpServletRequest) request;
			final String referrer = hsr.getHeader("Referer");
			boolean valid = true;
			if (referrer != null) {
				final String requestUrl = hsr.getRequestURL().toString();
				for (final Entry<Pattern, Pattern> entry : PATTERNS.entrySet()) {
					if (entry.getKey().matcher(requestUrl).matches() && !entry.getValue().matcher(referrer).matches()) {
						valid = false;
						break;
					}
				}
			}
			if (valid) {
				chain.doFilter(request, response);
			}
			else {
				// this is probably not the correct thing to do
				throw new ServletException("Hotlinking not allowed");
			}

		}

	}

	@Override
	public void destroy() {
	}

}