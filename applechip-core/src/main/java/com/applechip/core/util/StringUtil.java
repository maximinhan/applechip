package com.applechip.core.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {

	public static byte[] getBytesUtf8(String string) {
		return org.apache.commons.codec.binary.StringUtils.getBytesUtf8(string);
	}

	public static String newStringUtf8(byte[] bytes) {
		return org.apache.commons.codec.binary.StringUtils.newStringUtf8(bytes);
	}

	public static String encodeHexString(byte[] bytes) {
		return Hex.encodeHexString(bytes);
	}

	public static String removeTag(String string) {
		return string.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("\r|\n| ", "");
	}

}
