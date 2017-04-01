package com.github.cbuschka.tmply.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
	@Value("${durationUntilBucketExpiryAfterAccessInMillis:30000}")
	public int durationUntilBucketExpiryAfterAccessInMillis;

	@Value("${durationUntilBucketExpiryAfterCreationInMillis:300000}")
	public int durationUntilBucketExpiryAfterCreationInMillis;

	@Value("${maxBucketCount:9900}")
	public int maxBucketCount;

	@Value("${minBucketKeyLength:8}")
	public int minBucketKeyLength;

	@Value("${maxBucketKeyLength:100}")
	public int maxBucketKeyLength;

	@Value("${maxBucketValueLength:100}")
	public int maxBucketValueLength;
}
