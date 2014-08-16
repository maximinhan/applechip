package com.applechip.core.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.Getter;

import com.applechip.core.constant.CoreConstant;

@Getter
public class CoreProperties {
  private int executorCorePoolSize;
  private int executorMaxPoolSize;
  private int executorQueueCapacity;
  private int executorKeepAliveSeconds;
  private String executorThreadNamePrefix;
  private boolean cacheShared;
  private String cacheConfigLocation;
  private String scheduleCronExpression;
  private String scheduleTimeZoneId;
  private String scheduleThreadPool;
  private String storagePath;
  private String pushSignKeyIos;
  private int clearClientBeforeMinute;
  private String pushParamWp;

  public static CoreProperties getProperties(Properties properties) {
    return new CoreProperties(properties);
  }

  private CoreProperties(Properties properties) {
    this.setProperties(properties);
    this.setExecutor(properties);
    this.setCache(properties);
    this.setSchedule(properties);
  }

  private void setProperties(Properties properties) {
    this.storagePath = properties.getProperty("storage.path");
    Map<String, String> map = new HashMap<String, String>();
//    map.
//    new File("").
    this.pushSignKeyIos = properties.getProperty("push.signKey.ios");
    this.clearClientBeforeMinute = Integer.parseInt(properties.getProperty("clear.client.beforeMinute"));
    this.pushParamWp = properties.getProperty("push.param.wp");
  }

  private void setSchedule(Properties properties) {
    this.scheduleCronExpression = properties.getProperty("schedule.cronExpression");
    this.scheduleTimeZoneId = properties.getProperty("schedule.timeZoneId");
    this.scheduleThreadPool = properties.getProperty("schedule.threadPool");
  }

  private void setCache(Properties properties) {
    this.cacheShared = Boolean.parseBoolean(properties.getProperty("cache.shared"));
    this.cacheConfigLocation = properties.getProperty("cache.configLocation");
  }

  private void setExecutor(Properties properties) {
    this.executorCorePoolSize = Integer.parseInt(properties.getProperty("executor.corePoolSize"));
    this.executorMaxPoolSize = Integer.parseInt(properties.getProperty("executor.maxPoolSize"));
    this.executorQueueCapacity = Integer.parseInt(properties.getProperty("executor.queueCapacity"));
    this.executorKeepAliveSeconds = Integer.parseInt(properties.getProperty("executor.keepAliveSeconds"));
    this.executorThreadNamePrefix = properties.getProperty("executor.threadNamePrefix");
  }

  public String getDownloadPath() {
    return storagePath + CoreConstant.FILE_SEPARATOR + "download";
  }

  public String getGeoipFile() {
    return storagePath + CoreConstant.FILE_SEPARATOR + "geoip" + CoreConstant.FILE_SEPARATOR + "GeoIP2-City.mmdb";
  }
}
