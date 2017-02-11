package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.BucketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GarbageCollectBucketsBusinessService
{
	private static Logger log = LoggerFactory.getLogger(GarbageCollectBucketsBusinessService.class);

	@Autowired
	private BucketRepository bucketRepository;

	@Scheduled(initialDelay = 1000*30, fixedDelay = 1000*60*1)
	public void evictBuckets() {

		this.bucketRepository.evictBuckets();
	}
}
