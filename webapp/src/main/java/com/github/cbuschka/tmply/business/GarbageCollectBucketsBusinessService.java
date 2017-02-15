package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.bucket.BucketDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GarbageCollectBucketsBusinessService
{
	@Autowired
	private BucketDomainService bucketDomainService;

	@Scheduled(initialDelay = 1000 * 30, fixedDelay = 1000 * 30)
	@Transactional(propagation = Propagation.REQUIRED)
	public void evictBuckets()
	{
		this.bucketDomainService.evictBuckets();
	}
}
