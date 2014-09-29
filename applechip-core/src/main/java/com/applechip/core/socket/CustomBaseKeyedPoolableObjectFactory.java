package com.applechip.core.socket;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.applechip.core.util.StringUtil;

@Slf4j
public class CustomBaseKeyedPoolableObjectFactory extends BaseKeyedPooledObjectFactory<String, CustomSocketClient> {
	@Override
	public CustomSocketClient create(String key) throws Exception {
		log.trace("makeObject... {}", key);
		CustomSocketClient customSocketClient = new CustomSocketClient();
		connect(key, customSocketClient);
		return customSocketClient;
	}

	@Override
	public PooledObject<CustomSocketClient> wrap(CustomSocketClient value) {
		return new DefaultPooledObject<CustomSocketClient>(value);
	}

	@Override
	public void passivateObject(String key, PooledObject<CustomSocketClient> value) throws Exception {
		log.trace("passivateObject... {}", key);
		if (value.getObject().isConnected()) {
			log.trace("disconnect... {}", key);
			value.getObject().disconnect();
		}
	}

	@Override
	public boolean validateObject(String key, PooledObject<CustomSocketClient> value) {
		boolean validate = value.getObject().isAvailable();
		log.trace("validateObject... {}, {}", key, validate);
		return validate;
	}

	@Override
	public void destroyObject(String key, PooledObject<CustomSocketClient> value) throws Exception {
		log.trace("destroyObject... {}", key);
		if (value.getObject().isConnected()) {
			value.getObject().disconnect();
		}
	}

	@Override
	public void activateObject(String key, PooledObject<CustomSocketClient> value) throws Exception {
		log.trace("activateObject... {}", key);
		if (!value.getObject().isConnected()) {
			log.trace("reconnect... {}", key);
			connect(key, value.getObject());
		}
	}

	private void connect(String key, CustomSocketClient customSocketClient) throws Exception {
		String[] keys = StringUtil.split(key, "|");
		String hostname = keys[0];
		int port = Integer.parseInt(keys[1]);
		customSocketClient.connect(hostname, port);
	}

}
