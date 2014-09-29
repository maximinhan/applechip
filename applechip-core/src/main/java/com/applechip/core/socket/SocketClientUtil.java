package com.applechip.core.socket;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

import com.applechip.core.entity.Server;
import com.applechip.core.util.StringUtil;

@Slf4j
public class SocketClientUtil {

	private GenericKeyedObjectPool<String, CustomSocketClient> genericKeyedObjectPool;

	public SocketClientUtil(GenericKeyedObjectPool<String, CustomSocketClient> genericKeyedObjectPool) {
		super();
		this.genericKeyedObjectPool = genericKeyedObjectPool;
	}

	public boolean exists() {
		String key = getKey(new Server());
		CustomSocketClient customSocketClient = null;
		try {
			printPool(key);
			customSocketClient = genericKeyedObjectPool.borrowObject(key);
			return customSocketClient.exists();
		}
		catch (Exception e) {
			log.error("error... {}, key= {}", e.toString(), key);
			return true;
		}
		finally {
			disconnect(key, customSocketClient);
		}
	}

	private String getKey(Server server) {
		String host = StringUtil.defaultIfBlank(server.getPrivateHost(), server.getPublicHost());
		int port = server.getPort2();
		return String.format("%s|%d", host, port);
	}

	private void printPool(String key) {
		log.trace("sessionServerClientPool::key: {}, numActiveTotal: {}, numIdleTotal: {}, numActive: {}, numIdle: {}",
				key, genericKeyedObjectPool.getNumActive(), genericKeyedObjectPool.getNumIdle(),
				genericKeyedObjectPool.getNumActive(key), genericKeyedObjectPool.getNumIdle(key));
	}

	private void disconnect(String key, CustomSocketClient customSocketClient) {
		if (customSocketClient != null) {
			if (customSocketClient.isConnected())
				try {
					customSocketClient.disconnect();
				}
				catch (Exception ex) {
					log.debug(ex.toString());
				}
			try {
				genericKeyedObjectPool.returnObject(key, customSocketClient);
			}
			catch (Exception e) {
				log.debug(e.toString());
			}
		}
	}

}
