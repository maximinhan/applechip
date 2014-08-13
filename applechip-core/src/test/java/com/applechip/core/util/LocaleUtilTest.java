package com.applechip.core.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.StringUtils;
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
    System.out.println(Charsets.UTF_8.toString());
    System.out.println(Charsets.UTF_8.displayName());

//    UUID uuid = UUID.nameUUIDFromBytes(StringUtils.getBytesUtf8("applechip"));
//    System.out.println(uuid.toString());
//    ByteArrayOutputStream ba = new ByteArrayOutputStream(16);
//    DataOutputStream da = new DataOutputStream(ba);
//    da.writeLong(uuid.getMostSignificantBits());
//    da.writeLong(uuid.getLeastSignificantBits());
//    System.out.println();
//    
//    System.out.println(Long.toString(uuid.getMostSignificantBits(), 36));
//    System.out.println(Long.toString(uuid.getLeastSignificantBits(), 36));

//    char[] l = ByteBuffer.wrap(uuid.toString().getBytes()).asCharBuffer().array();
//    System.out.println(l.toString());
//    System.out.println(Character.toString(l, Character.FORMAT));
//    System.out.println(Long.toString(l, Character.MIN_RADIX));
//    System.out.println(Long.toString(l, Character.MAX_RADIX));
//    System.out.println(Long.toString(l, Character.SIZE));
//    System.out.println(Long.toString(l, Character.MIN_SUPPLEMENTARY_CODE_POINT));
    CryptoUtil cryptoUtil = new CryptoUtil();
//    Byte.SIZE;
    cryptoUtil.generateKey(SecurityUtil.getSecurityKey());
    System.out.println(cryptoUtil.encrypt("password"));
    System.out.println(cryptoUtil.decrypt("iaLQS4uk5SrS0oV+mm7s/g=="));

    // new CryptoUtil().generateKey(keyString, ivString);

//    System.out.println(Long.toString(l, 16));
//202cb962-ac59-375b-964b-07152d234b70
//    System.out.println(UUID.randomUUID().getMostSignificantBits());
//    System.out.println(UUID.randomUUID().toString());

    // User user = null;
    // System.out.println(StringUtils.defaultString(user.getId()));
  }

}
