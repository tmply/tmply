package com.github.cbuschka.tmply.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig
{
	@Value("${minBucketKeyLength:8}")
	public int minBucketKeyLength;

	@Value("${maxBucketKeyLength:100}")
	public int maxBucketKeyLength;

	@Value("${maxBucketValueLength:100}")
	public int maxBucketValueLength;
}
