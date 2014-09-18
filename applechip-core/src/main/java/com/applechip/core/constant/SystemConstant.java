package com.applechip.core.constant;

import java.nio.charset.Charset;

import org.apache.commons.codec.CharEncoding;

public class SystemConstant {

	// system constant
	public static final Charset CHARSET = Charset.forName(CharEncoding.UTF_8);

	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	protected static final String USER_HOME = System.getProperty("user.home");

}
