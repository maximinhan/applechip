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

	private static Map<String, MethodState> methodStateMap = new ConcurrentHashMap<String, MethodState>();

	private boolean enabled;

	private long frequency = 10;

	private long threshold = 1000;

	public MonitoringInterceptor(boolean enabled, long frequency, long threshold) {
		super();
		this.enabled = enabled;
		this.frequency = frequency;
		this.threshold = threshold;
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (this.enabled) {
			return methodInvocationTrace(methodInvocation);
		}
		else {
			return methodInvocation.proceed();
		}

	}

	private Object methodInvocationTrace(MethodInvocation methodInvocation) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			log.info("methodInvocationTrace start:{}", start);
			return methodInvocation.proceed();
		}
		finally {
			methodInvocationTraceMethod(methodInvocation.getMethod().getName(), System.currentTimeMillis() - start);
		}
	}

	private void methodInvocationTraceMethod(String method, long elapsedTime) {
		MethodState methodState = getMethodState(method);
		methodState.setCount(methodState.getCount() + 1);
		methodState.setTotalTime(methodState.getTotalTime() + elapsedTime);
		if (elapsedTime > methodState.getMaxTime()) {
			methodState.setMaxTime(elapsedTime);
		}
		if (elapsedTime > this.threshold) {
			log.warn("method warning:{}, count={}, lastTime={}, maxTime={}", method, methodState.getCount(),
					elapsedTime, methodState.getMaxTime());
		}
		if (methodState.getCount() % this.frequency == 0) {
			long avgTime = methodState.getTotalTime() / methodState.getCount();
			long runningAvg = (methodState.getTotalTime() - methodState.getLastTotalTime()) / this.frequency;
			log.info("method:{}, count={}, lastTime={}, avgTime={}, runningAvg={}, maxTime={}", method,
					methodState.getCount(), elapsedTime, avgTime, runningAvg, methodState.getMaxTime());
			methodState.setLastTotalTime(methodState.getTotalTime());
		}
	}

	private MethodState getMethodState(String method) {
		MethodState methodState = methodStateMap.get(method);
		if (methodState == null) {
			methodState = new MethodState();
			methodStateMap.put(method, methodState);
		}
		return methodState;
	}

	@Getter
	@Setter
	private static class MethodState {
		private long count;

		private long totalTime;

		private long lastTotalTime;

		private long maxTime;
	}
}