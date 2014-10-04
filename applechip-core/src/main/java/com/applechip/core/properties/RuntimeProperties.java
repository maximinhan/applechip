package com.applechip.core.properties;

import org.apache.commons.configuration.PropertiesConfiguration;

public class RuntimeProperties extends ApplicationProperties {
	private static final long serialVersionUID = 8269657777859176740L;

	private transient PropertiesConfiguration propertiesConfiguration;

	public RuntimeProperties(PropertiesConfiguration propertiesConfiguration) {
		super();
		this.propertiesConfiguration = propertiesConfiguration;
	}

	@Override
	public String getServerAddress() {
		return propertiesConfiguration.getString("server.address", super.getServerAddress());
	}

	@Override
	public String getPushMdmKey() {
		return propertiesConfiguration.getString("push.mdm.signKey", super.getPushMdmKey());
	}

	@Override
	public String getPushWpParam() {
		return propertiesConfiguration.getString("push.wp.param", super.getPushWpParam());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuntimeProperties [getServerAddress()=");
		builder.append(getServerAddress());
		builder.append(", getPushMdmKey()=");
		builder.append(getPushMdmKey());
		builder.append(", getPushWpParam()=");
		builder.append(getPushWpParam());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
