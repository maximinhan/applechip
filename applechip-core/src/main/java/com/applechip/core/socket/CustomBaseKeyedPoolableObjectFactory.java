package com.applechip.core.socket;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

import com.applechip.core.util.StringUtil;

@Slf4j
public class CustomBaseKeyedPoolableObjectFactory extends BaseKeyedPoolableObjectFactory<String, CustomSocketClient> {

	@Override
	public CustomSocketClient makeObject(String key) throws Exception {
		log.trace("makeObject... {}", key);
		CustomSocketClient customSocketClient = new CustomSocketClient();
		connect(key, customSocketClient);
		return customSocketClient;
	}

	@Override
	public void destroyObject(String key, CustomSocketClient customSocketClient) throws Exception {
		log.trace("destroyObject... {}", key);
		if (customSocketClient.isConnected()) {
			customSocketClient.disconnect();
		}
	}

	@Override
	public boolean validateObject(String key, CustomSocketClient customSocketClient) {
		boolean validate = customSocketClient.isAvailable();
		log.trace("validateObject... {}, {}", key, validate);
		return validate;
	}

	@Override
	public void activateObject(String key, CustomSocketClient customSocketClient) throws Exception {
		log.trace("activateObject... {}", key);
		if (!customSocketClient.isConnected()) {
			log.trace("reconnect... {}", key);
			connect(key, customSocketClient);
		}
	}

	@Override
	public void passivateObject(String key, CustomSocketClient customSocketClient) throws Exception {
		log.trace("passivateObject... {}", key);
		if (customSocketClient.isConnected()) {
			log.trace("disconnect... {}", key);
			customSocketClient.disconnect();
		}
	}

	private void connect(String key, CustomSocketClient customSocketClient) throws Exception {
		String[] keys = StringUtil.split(key, "|");
		String hostname = keys[0];
		int port = Integer.parseInt(keys[1]);
		customSocketClient.connect(hostname, port);
	}

}
