package com.applechip.core.properties;

import lombok.Getter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Value;

import com.applechip.core.constant.ApplicationConstant;
import com.applechip.core.constant.SystemConstant;
import com.applechip.core.util.StringUtil;

@Getter
@ToString
public class ApplicationProperties {
	@Value("${storage.path}")
	private String storagePath;

	@Value("${download.name}")
	private String downloadName;

	@Value("${resource.name}")
	private String resourceName;

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

	@Value("${push.mdm.key}")
	private String pushMdmKey;

	@Value("${push.wp.param}")
	private String pushWpParam;

	@Value("${server.address}")
	private String serverAddress;

	@Value("${redis.hostName}")
	private String redisHostName;

	@Value("${redis.port}")
	private int redisPort;

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

	public String getGeoipFilePath() {
		return this.getResourcePath() + SystemConstant.FILE_SEPARATOR + "GeoIP2-City.mmdb";
	}

}
