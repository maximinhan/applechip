package com.applechip.core.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.util.concurrent.Monitor.Guard;

@Slf4j
public class CustomRedisCache implements Cache {@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getNativeCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueWrapper get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Object key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	//
	// @Test
	// public void clearTest() {
	// Assert.assertNotNull(redisCacheManager);
	// Cache cache = redisCacheManager.getCache("user");
	// Assert.assertNotNull(cache);
	// cache.clear();
	// Assert.assertNotNull(cache);
	// }
	//
	// @Test
	// public void getUserFromCache() {
	//
	// Stopwatch sw = new Stopwatch("initial User");
	// sw.start();
	// User user1 = userRepository.getUser("debop", 100);
	// sw.stop();
	//
	// sw = new Stopwatch("from Cache");
	// sw.start();
	// User user2 = userRepository.getUser("debop", 100);
	// sw.stop();
	//
	// Assert.assertEquals(user1.getUsername(), user2.getUsername());
	// }
	//
	// @Test
	// public void componentConfigurationTest() {
	// Assert.assertNotNull(redisCacheManager);
	// Cache cache = redisCacheManager.getCache("user");
	// Assert.assertNotNull(cache);
	//
	// cache.evict("debop");
	//
	// Assert.assertNotNull(userRepository);
	// }

	// @Getter
	// private String name;
	//
	// @Getter
	// private int expireSeconds;
	//
	// private RedisTemplate redisTemplate;
	//
	// public CustomRedisCache(String name, RedisTemplate redisTemplate) {
	// this(name, redisTemplate, 300);
	// }
	//
	// public CustomRedisCache(String name, RedisTemplate redisTemplate, int expireSeconds) {
	// Guard.shouldNotBeEmpty(name, "name");
	// Guard.shouldNotBeNull(redisTemplate, "redisTemplate");
	//
	// this.name = name;
	// this.redisTemplate = redisTemplate;
	//
	// if (log.isDebugEnabled())
	// log.debug("MongoCache를 생성합니다. name=[{}], mongodb=[{}]", name, redisTemplate);
	// }
	//
	// @Override
	// public Object getNativeCache() {
	// return redisTemplate;
	// }
	//
	// public String getKey(Object key) {
	// return name + ":" + key;
	// }
	//
	// @Override
	// public ValueWrapper get(Object key) {
	// Guard.shouldNotBeNull(key, "key");
	// if (log.isDebugEnabled())
	// log.debug("캐시 키[{}] 값을 구합니다...", key);
	//
	// Object result = redisTemplate.opsForValue().get(getKey(key));
	//
	// SimpleValueWrapper wrapper = null;
	// if (result != null) {
	// if (log.isDebugEnabled())
	// log.debug("캐시 값을 로드했습니다. key=[{}]", key);
	// wrapper = new SimpleValueWrapper(result);
	// }
	// return wrapper;
	// }
	//
	// @Override
	// @SuppressWarnings("unchecked")
	// public void put(Object key, Object value) {
	// Guard.shouldNotBeNull(key, "key");
	//
	// if (log.isDebugEnabled())
	// log.debug("캐시에 값을 저장합니다. key=[{}], value=[{}]", key, value);
	//
	// redisTemplate.opsForValue().set(getKey(key), value, expireSeconds);
	// }
	//
	// @Override
	// @SuppressWarnings("unchecked")
	// public void evict(Object key) {
	// Guard.shouldNotBeNull(key, "key");
	// if (log.isDebugEnabled())
	// log.debug("지정한 키[{}]의 캐시를 삭제합니다...", key);
	//
	// try {
	// redisTemplate.delete(key);
	// }
	// catch (Exception e) {
	// log.error("캐시 항목 삭제에 실패했습니다. key=" + key, e);
	// }
	// }
	//
	// @Override
	// @SuppressWarnings("unchecked")
	// public void clear() {
	// if (log.isDebugEnabled())
	// log.debug("모든 캐시를 삭제합니다...");
	// try {
	// redisTemplate.execute(new RedisCallback() {
	// @Override
	// public Object doInRedis(RedisConnection connection) throws DataAccessException {
	// connection.flushAll();
	// return null;
	// }
	// });
	// }
	// catch (Exception e) {
	// log.warn("모든 캐시를 삭제하는데 실패했습니다.", e);
	// }
	// }
}
