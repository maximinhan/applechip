package com.applechip.core.util;

import org.apache.http.conn.util.InetAddressUtils;

public class InetAddressUtil {

  private InetAddressUtil() {}

  public static boolean isIPv4Address(String input) {
    return InetAddressUtils.isIPv4Address(input);
  }

  public static boolean isIPv6StdAddress(String input) {
    return InetAddressUtils.isIPv6StdAddress(input);
  }

  public static boolean isIPv6HexCompressedAddress(String input) {
    return InetAddressUtils.isIPv6HexCompressedAddress(input);
  }

  public static boolean isIPv6Address(String input) {
    return InetAddressUtils.isIPv6Address(input);
  }

}
