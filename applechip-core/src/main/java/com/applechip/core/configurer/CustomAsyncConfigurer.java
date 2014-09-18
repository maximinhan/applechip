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

import com.applechip.core.properties.ApplicationProperties;

@Configuration
@EnableAsync(proxyTargetClass = true, mode = AdviceMode.PROXY, order = Ordered.LOWEST_PRECEDENCE, annotation = Async.class)
public class CustomAsyncConfigurer implements AsyncConfigurer {

  @Autowired
  private ApplicationProperties applicationProperties;

  @Override
  @Bean
  public Executor getAsyncExecutor() {
    Executor executor = null;
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(applicationProperties.getExecutorCorePoolSize());
    threadPoolTaskExecutor.setMaxPoolSize(applicationProperties.getExecutorMaxPoolSize());
    threadPoolTaskExecutor.setQueueCapacity(applicationProperties.getExecutorQueueCapacity());
    threadPoolTaskExecutor.setKeepAliveSeconds(applicationProperties.getExecutorKeepAliveSeconds());
    threadPoolTaskExecutor.setThreadNamePrefix(applicationProperties.getExecutorThreadNamePrefix());
    threadPoolTaskExecutor.initialize();
    executor = threadPoolTaskExecutor;
    return executor;
  }

}
