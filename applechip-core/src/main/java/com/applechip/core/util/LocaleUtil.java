package com.applechip.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

public class LocaleUtil {
	public static List<Locale> getSupportLocaleList(String... supportedLocales) {
		List<Locale> list = new ArrayList<Locale>();
		for (String string : supportedLocales) {
			list.add(getStringToLocale(string));
		}
		return comparator(list);
	}

	public static Locale getStringToLocale(String string) {
		Locale locale = null;
		if (StringUtil.contains(string, '_')) {
			String[] strings = StringUtil.splitPreserveAllTokens(string, '_');
			locale = new Locale(strings[0], strings[1]);
		}
		else {
			locale = new Locale(string);
		}
		if (!getLocaleList().contains(locale)) {
			locale = Locale.getDefault();
		}
		return locale;
	}

	private static List<Locale> getLocaleList() {
		List<Locale> list = new ArrayList<Locale>();
		for (Locale locale : Locale.getAvailableLocales()) {
			if (StringUtil.contains(locale.toString(), '_')) {
				locale = new Locale(locale.getLanguage(), locale.getCountry());
			}
			list.add(locale);
		}
		return comparator(list);
	}

	private static List<Locale> comparator(List<Locale> list) {
		list = new ArrayList<Locale>(new HashSet<Locale>(list));
		Collections.sort(list, new Comparator<Locale>() {
			public int compare(Locale locale1, Locale locale2) {
				return locale1.toString().compareToIgnoreCase(locale2.toString());
			}
		});
		return list;
	}

	public static void setLocale(Locale locale, HttpServletRequest request) {
		if (locale == null) {
			return;
		}
		Config.set(request.getSession(), Config.FMT_LOCALE, locale);
		WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		LocaleContextHolder.setLocale(locale);
	}

}
