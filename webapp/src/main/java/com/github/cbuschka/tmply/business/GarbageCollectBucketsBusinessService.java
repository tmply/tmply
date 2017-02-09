package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GarbageCollectBucketsBusinessService
{
	@Autowired
	private BucketRepository bucketRepository;

	@Scheduled(initialDelay = 1000*30, fixedDelay = 1000*5)
	public void evictBuckets() {
		this.bucketRepository.evictBuckets();
	}
}
