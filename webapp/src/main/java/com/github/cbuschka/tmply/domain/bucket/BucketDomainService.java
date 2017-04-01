package com.github.cbuschka.tmply.domain.bucket;

import com.github.cbuschka.tmply.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public class BucketDomainService
{
	@Autowired
	private AppConfig appConfig;

	@Autowired
	private BucketRepository bucketRepository;

	private static Logger log = LoggerFactory.getLogger(BucketDomainService.class);

	private Map<String, BucketEntity> buckets = new HashMap<>();

	public synchronized BucketEntity putBucket(String bucketName, String data)
	{
		long now = System.currentTimeMillis();

		BucketEntity bucket = this.bucketRepository.findByBucketName(bucketName);
		if (bucket == null)
		{
			long count = this.bucketRepository.count();
			if (count >= this.appConfig.maxBucketCount)
			{
				throw new IllegalStateException("No bucket slot available. Retry later.");
			}

			bucket = new BucketEntity(bucketName, data, new Date(now + appConfig.durationUntilBucketExpiryAfterCreationInMillis));
			this.bucketRepository.save(bucket);
		}
		else
		{
			bucket.setData(data);
			bucket.setExpiryTime(new Date(now + appConfig.durationUntilBucketExpiryAfterCreationInMillis));
		}

		log.info("Bucket {} added or updated.", bucketName);

		return bucket;
	}

	public BucketEntity getBucket(String bucketName)
	{
		long now = System.currentTimeMillis();
		BucketEntity bucket = this.bucketRepository.findByBucketName(bucketName);
		if (bucket == null || bucket.getExpiryTime().getTime() < now)
		{
			log.info("Accessed bucketEntity {} not found/ has expired.", bucketName);
			return null;
		}

		bucket.setExpiryTime(new Date(now + appConfig.durationUntilBucketExpiryAfterAccessInMillis));
		log.info("Bucket {} accessed.", bucketName);

		return bucket;
	}

	public void evictBuckets()
	{
		long countBeforeviction = this.bucketRepository.count();

		this.bucketRepository.deleteByExpiryTimeLessThan(new Date());

		long countAfterEviction = this.bucketRepository.count();

		log.info("{} bucket(s) in use after eviction, {} removed.", countAfterEviction, countBeforeviction - countAfterEviction);
	}

	public void remove(String bucketName)
	{
		this.bucketRepository.deleteByBucketName(bucketName);
	}

	public int maxBucketCount()
	{
		return appConfig.maxBucketCount;
	}
}
