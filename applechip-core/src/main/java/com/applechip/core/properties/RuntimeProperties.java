package com.applechip.core.properties;

import org.apache.commons.configuration.PropertiesConfiguration;

public class RuntimeProperties extends ApplicationProperties {

	private PropertiesConfiguration propertiesConfiguration;

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
		builder.append("RuntimeProperties [getServerAddress()=").append(getServerAddress())
				.append(", getPushMdmKey()=").append(getPushMdmKey()).append(", getPushWpParam()=")
				.append(getPushWpParam()).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
}
