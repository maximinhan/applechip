package com.applechip.core.configurer.support;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectJRole {
	@Pointcut("execution (* com..*.*Service.*(..))")
	//"execution(* com..*.*Service.*(..))"
	public void servicePointcut() {
	}
}