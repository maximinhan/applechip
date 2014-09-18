package com.applechip.core.constant;

public class ApplicationConstant extends SystemConstant {

	public static final String PERSISTENCE_UNIT_NAME = "PERSISTENCE_UNIT";

	public static final String APPLECHIP_HOME = String.format("%s%s.applechip", USER_HOME, FILE_SEPARATOR);

	private static final String CONFIG_PROPERTIES = "config.properties";

	private static final String APPLICATION_PROPERTIES = "application.properties";

	public static final String CONFIG_PROPERTIES_PATH = String.format("classpath:%s", CONFIG_PROPERTIES);

	public static final String CONFIG_PROPERTIES_PRODUCTION = String.format("file:%s%s%s", APPLECHIP_HOME,
			FILE_SEPARATOR, CONFIG_PROPERTIES);

	public static final String APPLICATION_PROPERTIES_PATH = String.format("classpath:properties%s%s", FILE_SEPARATOR,
			APPLICATION_PROPERTIES);

}
