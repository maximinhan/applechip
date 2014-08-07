package com.applechip.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

@Slf4j
public class LocaleUtilTest {

  // @Ignore
  @Test
  public void testGetAvailableLocaleList() throws Exception {
    List<Locale> list = LocaleUtil.getSupportLocaleList("d");
    for (Locale locale : list) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("|" + locale.toString());
      stringBuilder.append("|" + locale.getDisplayName(Locale.ENGLISH));
      stringBuilder.append("|" + locale.getDisplayName(Locale.KOREAN));
      try {
        stringBuilder.append("|" + locale.getISO3Language());
        stringBuilder.append("|" + locale.getISO3Country());
      } catch (MissingResourceException e) {
        stringBuilder.append("|");
        stringBuilder.append("|");
      }
      System.out.println(list.indexOf(locale) + stringBuilder.toString());
    }
  }

  @Test
  public void testConvertStringToLocale() throws Exception {
    System.out.println(LocaleUtil.getStringToLocale("ko"));
    System.out.println(LocaleUtil.getStringToLocale("ko_"));
    System.out.println(LocaleUtil.getStringToLocale("ko_KR"));
    System.out.println(LocaleUtil.getStringToLocale("ko_KR_KO"));
    System.out.println(LocaleUtil.getStringToLocale("ko_kr"));
    System.out.println(LocaleUtil.getStringToLocale("kr"));
    System.out.println(LocaleUtil.getStringToLocale("kr_ko"));
    // throw new RuntimeException("not yet implemented");
  }

  @Test
  public void testGetSupportLocaleList() throws Exception {
    List<String> list = new ArrayList<String>();
    list.add("ko");
    list.add("ko_");
    list.add("k_o");
    list.add("ko_KR");
    list.add("ko_kr");
    list.add("ko_kr_oo");
    for (Locale locale : LocaleUtil.getSupportLocaleList(list.toArray(new String[] {}))) {
      System.out.println(locale.toString());
    }
  }

}
