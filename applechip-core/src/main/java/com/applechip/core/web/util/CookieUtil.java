package com.applechip.core.web.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

public class CookieUtil {
	private Pattern cookiePattern = Pattern.compile("([^=]+)=([^\\;]*);?\\s?");

	public void parseCookieString(String cookies, List<Cookie> cookieList) {
		Matcher matcher = cookiePattern.matcher(cookies);
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			System.out.println("matched: " + matcher.group(0));
			for (int groupIndex = 0; groupIndex <= groupCount; ++groupIndex) {
				System.out.println("group[" + groupIndex + "]=" + matcher.group(groupIndex));
			}
			String cookieKey = matcher.group(1);
			String cookieValue = matcher.group(2);
			Cookie cookie = new Cookie(cookieKey, cookieValue);
			cookieList.add(cookie);
			// modify other cookie properties based on remaing match groups using
			// using Doug's code encapsulated into a separate method
		}
	}
}
