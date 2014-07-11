package com.applechip.core.configurer;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.applechip.core.properties.CoreProperties;

@Configuration
@EnableAsync(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.LOWEST_PRECEDENCE, annotation = Async.class)
public class CustomAsyncConfigurer implements AsyncConfigurer {

  @Autowired
  private CoreProperties baseProperties;

  @Override
  @Bean
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(baseProperties.getExecutorCorePoolSize());
    executor.setMaxPoolSize(baseProperties.getExecutorMaxPoolSize());
    executor.setQueueCapacity(baseProperties.getExecutorQueueCapacity());
    executor.setKeepAliveSeconds(baseProperties.getExecutorKeepAliveSeconds());
    executor.setThreadNamePrefix(baseProperties.getExecutorThreadNamePrefix());
    executor.initialize();
    return executor;
  }

}
