package com.applechip.core.manager;

import java.security.Guard;
import java.util.Collection;

import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.collect.Lists;

public class CustomRedisCacheManager extends AbstractTransactionSupportingCacheManager {

	@Override
	protected Collection<? extends Cache> loadCaches() {
		// TODO Auto-generated method stub
		return null;
	}

//	private RedisTemplate redisTemplate;
//
//	private int expireSeconds;
//
//	public CustomRedisCacheManager(RedisTemplate redisTemplate) {
//		this(redisTemplate, 300);
//	}
//
//	public CustomRedisCacheManager(RedisTemplate redisTemplate, int expireSeconds) {
//		Guard.shouldNotBeNull(redisTemplate, "redisTemplate");
//		this.redisTemplate = redisTemplate;
//		this.expireSeconds = expireSeconds;
//	}
//
//	@Override
//	protected Collection<? extends Cache> loadCaches() {
//		Collection<Cache> caches = Lists.newArrayList();
//
//		for (String name : getCacheNames()) {
//			caches.add(new RedisCache(name, redisTemplate, expireSeconds));
//		}
//		return caches;
//	}
//
//	@Override
//	public Cache getCache(String name) {
//		synchronized (this) {
//			Cache cache = super.getCache(name);
//			if (cache == null) {
//				cache = new RedisCache(name, redisTemplate, expireSeconds);
//				addCache(cache);
//			}
//			return cache;
//		}
//	}
}
