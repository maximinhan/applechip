package com.applechip.core.properties;

import java.util.Properties;

import com.applechip.core.constant.BaseConstant;

public class ApplechipProperties {
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

  public static ApplechipProperties getProperties(Properties properties) {
    return new ApplechipProperties(properties);
  }

  private ApplechipProperties(Properties properties) {
    this.init(properties);
  }

  private void init(Properties properties) {
    this.executorCorePoolSize = Integer.parseInt(properties.getProperty("executor.corePoolSize"));
    this.executorMaxPoolSize = Integer.parseInt(properties.getProperty("executor.maxPoolSize"));
    this.executorQueueCapacity = Integer.parseInt(properties.getProperty("executor.queueCapacity"));
    this.executorKeepAliveSeconds = Integer.parseInt(properties.getProperty("executor.keepAliveSeconds"));
    this.executorThreadNamePrefix = properties.getProperty("executor.threadNamePrefix");
    this.cacheShared = Boolean.parseBoolean(properties.getProperty("cache.shared"));
    this.cacheConfigLocation = properties.getProperty("cache.configLocation");
    this.scheduleCronExpression = properties.getProperty("schedule.cronExpression");
    this.scheduleTimeZoneId = properties.getProperty("schedule.timeZoneId");
    this.scheduleThreadPool = properties.getProperty("schedule.threadPool");
    this.storagePath = properties.getProperty("storage.path");
    this.pushSignKeyIos = properties.getProperty("push.signKey.ios");
    this.clearClientBeforeMinute = Integer.parseInt(properties.getProperty("clear.client.beforeMinute"));
    this.pushParamWp = properties.getProperty("push.param.wp");
  }

  public int getExecutorCorePoolSize() {
    return executorCorePoolSize;
  }

  public int getExecutorMaxPoolSize() {
    return executorMaxPoolSize;
  }

  public int getExecutorQueueCapacity() {
    return executorQueueCapacity;
  }

  public int getExecutorKeepAliveSeconds() {
    return executorKeepAliveSeconds;
  }

  public String getExecutorThreadNamePrefix() {
    return executorThreadNamePrefix;
  }

  public boolean isCacheShared() {
    return cacheShared;
  }

  public String getCacheConfigLocation() {
    return cacheConfigLocation;
  }

  public String getScheduleCronExpression() {
    return scheduleCronExpression;
  }

  public String getScheduleTimeZoneId() {
    return scheduleTimeZoneId;
  }

  public String getScheduleThreadPool() {
    return scheduleThreadPool;
  }

  public String getStoragePath() {
    return storagePath;
  }

  public String getDownloadPath() {
    return storagePath + BaseConstant.FILE_SEPARATOR + "download";
  }

  public String getPushSignKeyIos() {
    return pushSignKeyIos;
  }

  public int getClearClientBeforeMinute() {
    return clearClientBeforeMinute;
  }

  public String getPushParamWp() {
    return pushParamWp;
  }

  public String getGeoipFile() {
    return storagePath + BaseConstant.FILE_SEPARATOR + "geoip" + BaseConstant.FILE_SEPARATOR + "GeoIP2-City.mmdb";
  }
}
