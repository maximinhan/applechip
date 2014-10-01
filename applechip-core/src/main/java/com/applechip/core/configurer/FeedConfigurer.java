package com.applechip.core.configurer;

import org.springframework.context.annotation.Bean;

import com.rometools.fetcher.FeedFetcher;
import com.rometools.fetcher.impl.HttpClientFeedFetcher;
import com.rometools.fetcher.impl.LinkedHashMapFeedInfoCache;

public class FeedConfigurer {

	@Bean
	public FeedFetcher feedFetcher() {
		HttpClientFeedFetcher httpFeedFetcher = new HttpClientFeedFetcher(LinkedHashMapFeedInfoCache.getInstance());
		httpFeedFetcher.setUserAgent("");
		return httpFeedFetcher;
	}
}
