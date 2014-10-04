package com.applechip.core.properties;

import java.util.List;
import java.util.Properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;

import com.applechip.core.AbstractObject;
import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.constant.SystemConstant;
import com.applechip.core.util.StringUtil;

@Getter
public class ApplicationProperties extends AbstractObject {
  private static final long serialVersionUID = 6739078137459063185L;

  @Value("${server.id}")
  private String serverId;

  @Value("${storage.path}")
  private String storagePath;

  @Value("${download.name}")
  private String downloadName;

  @Value("${resource.name}")
  private String resourceName;

  @Value("${upload.name}")
  private String uploadName;

  @Value("${properties.refresh.delay}")
  private long refreshDelay;

  @Value("${executor.corePoolSize}")
  private int executorCorePoolSize;

  @Value("${executor.maxPoolSize}")
  private int executorMaxPoolSize;

  @Value("${executor.queueCapacity}")
  private int executorQueueCapacity;

  @Value("${executor.keepAliveSeconds}")
  private int executorKeepAliveSeconds;

  @Value("${executor.threadNamePrefix}")
  private String executorThreadNamePrefix;

  @Value("${schedule.cronExpression}")
  private String scheduleCronExpression;

  @Value("${schedule.timeZoneId}")
  private String scheduleTimeZoneId;

  @Value("${schedule.threadPool}")
  private String scheduleThreadPool;

  @Value("${monitoring.enabled}")
  private boolean monitoringEnabled;

  @Value("${monitoring.frequency}")
  private int monitoringFrequency;

  @Value("${monitoring.threshold}")
  private int monitoringThreshold;

  @Value("${push.mdm.key}")
  private String pushMdmKey;

  @Value("${push.wp.param}")
  private String pushWpParam;

  @Value("${server.address}")
  private String serverAddress;

  @Value("${redis.host}")
  private String redisHost;

  @Value("${redis.port}")
  private int redisPort;

  @Value("${redis.timeout}")
  private int redisTimeout;

  @Value("${redis.database}")
  private int redisDatabase;

  @Value("${redis.usePool}")
  private boolean redisUsePool;

  @Value("${redis.pool.testWhileIdle}")
  private boolean redisPoolTestWhileIdle;

  @Value("${redis.pool.minEvictableIdleTimeMillis}")
  private int redisPoolMinEvictableIdleTimeMillis;

  @Value("${redis.pool.timeBetweenEvictionRunsMillis}")
  private int redisPoolTimeBetweenEvictionRunsMillis;

  @Value("${redis.pool.numTestsPerEvictionRun}")
  private int redisPoolNumTestsPerEvictionRun;

  @Value("${ehcache.shared}")
  private boolean ehcacheShared;

  @Value("${ehcache.configLocation}")
  private String ehcacheConfigLocation;

  @Value("#{T(java.util.Arrays).asList('${ehcache.cacheNames}')}")
  private List<String> ehcacheCacheNames;

  @Value("#{T(com.applechip.core.util.PropertiesLoaderUtil).stringToProperties('${database.transactionProperties}', '|', '=')}")
  private Properties databaseTransactionProperties;

  public String getStoragePath() {
    String storagePath = this.storagePath;
    if (StringUtil.isBlank(storagePath)) {
      storagePath = ApplicationConstant.APPLECHIP_HOME;
    }
    return storagePath;
  }

  public String getDownloadPath() {
    return this.getStoragePath() + SystemConstant.FILE_SEPARATOR + this.downloadName;
  }

  public String getResourcePath() {
    return this.getStoragePath() + SystemConstant.FILE_SEPARATOR + this.resourceName;
  }

  public String getUploadPath() {
    return this.getStoragePath() + SystemConstant.FILE_SEPARATOR + this.uploadName;
  }

  public String getGeoipFilePath() {
    return this.getResourcePath() + SystemConstant.FILE_SEPARATOR + "GeoLite2-City.mmdb";
  }

}
