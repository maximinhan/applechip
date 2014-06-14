package com.applechip.core.configurer;

import java.util.TimeZone;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.applechip.core.properties.BaseProperties;

@Configuration
@EnableScheduling
public class CustomSchedulingConfigurer implements SchedulingConfigurer {

  @Autowired
  private BaseProperties baseProperties;

//  @Autowired
//  private ClearData clearData;

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    TimeZone timeZone = TimeZone.getDefault();
    String timeZoneId = baseProperties.getScheduleTimeZoneId();
    if (StringUtils.isNotBlank(timeZoneId)) {
      timeZone = TimeZone.getTimeZone(timeZoneId);
    }
    CronTrigger cronTrigger = new CronTrigger(baseProperties.getScheduleCronExpression(), timeZone);
    taskRegistrar.setScheduler(Executors.newScheduledThreadPool(Integer.parseInt(baseProperties.getScheduleThreadPool())));
    taskRegistrar.addCronTask(new CronTask(new Runnable() {
      public void run() {
//        clearData.clearClient(baseProperties.getClearClientBeforeMinute());
      }
    }, cronTrigger));
  }

}