package com.github.cbuschka.tmply.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public class BucketDomainService
{
	public static final int MAX_BUCKET_COUNT = 9900;
	private static final long MILLIS_UNTIL_EXPIRY_AFTER_ACCESS = 1000 * 30;
	private static final long MILLIS_UNTIL_EXPIRATION_AFTER_CREATION = 1000 * 60 * 5;

	@Autowired
	private BucketRepository bucketRepository;

	private static Logger log = LoggerFactory.getLogger(BucketDomainService.class);

	private Map<String, BucketEntity> buckets = new HashMap<>(MAX_BUCKET_COUNT * 2);

	public synchronized BucketEntity putBucket(String bucketName, String data)
	{
		long now = System.currentTimeMillis();

		BucketEntity bucket = this.bucketRepository.findByBucketName(bucketName);
		if (bucket == null)
		{
			long count = this.bucketRepository.count();
			if (count >= MAX_BUCKET_COUNT)
			{
				throw new IllegalStateException("No bucket slot available. Retry later.");
			}

			bucket = new BucketEntity(bucketName, data, new Date(now + MILLIS_UNTIL_EXPIRATION_AFTER_CREATION));
			this.bucketRepository.save(bucket);
		}
		else
		{
			bucket.setData(data);
			bucket.setExpiryTime(new Date(now + MILLIS_UNTIL_EXPIRATION_AFTER_CREATION));
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

		bucket.setExpiryTime(new Date(now + MILLIS_UNTIL_EXPIRY_AFTER_ACCESS));
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
}
