package com.applechip.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
@Getter
@Setter
public class MonitoringInterceptor implements MethodInterceptor {

	private static Map<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();

	private boolean enabled;

	private long statLogFrequency = 10;

	private long warningThreshold = 1000;

	private String systemName = "";

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (this.enabled) {
			return invokeUnderTrace(invocation);
		}
		else {
			return invocation.proceed();
		}

	}

	private Object invokeUnderTrace(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			return invocation.proceed();
		}
		finally {
			traceMethod(invocation.getMethod().getName(), System.currentTimeMillis() - start);
		}
	}

	private void traceMethod(String methodName, long elapsedTime) {
		MethodStats stats = methodStats.get(methodName);
		if (stats == null) {
			stats = new MethodStats();
			methodStats.put(methodName, stats);
		}
		stats.count++;
		stats.totalTime += elapsedTime;
		if (elapsedTime > stats.maxTime) {
			stats.maxTime = elapsedTime;
		}

		if (elapsedTime > warningThreshold) {
			log.warn(String.format("[%s]method warning:%s, count=%d, lastTime=%d, maxTime=%d", this.systemName,
					methodName, stats.count, elapsedTime, stats.maxTime));
		}

		if (stats.count % statLogFrequency == 0) {
			long avgTime = stats.totalTime / stats.count;
			long runningAvg = (stats.totalTime - stats.lastTotalTime) / statLogFrequency;
			log.info(String.format("[%s]method:%s, count=%d, lastTime=%d, avgTime=%d, runningAvg=%d, maxTime=%d",
					this.systemName, methodName, stats.count, elapsedTime, avgTime, runningAvg, stats.maxTime));

			stats.lastTotalTime = stats.totalTime;
		}
	}

	private static class MethodStats {

		private long count;

		private long totalTime;

		private long lastTotalTime;

		private long maxTime;

	}
}