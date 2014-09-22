package com.applechip.core.configurer;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomAspectJConfigurer {

	@Pointcut("execution (* com..*.*Service.*(..))")
	public void servicePointcut() {
	}

}
