package com.github.cbuschka.tmply.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
public class BucketRepository
{
	private static final int MAX_BUCKET_COUNT = 1000;
	private static final long MILLIS_UNTIL_EXPIRY_AFTER_ACCESS = 1000 * 30;
	private static final long MILLIS_UNTIL_EXPIRATION_AFTER_CREATION = 1000 * 60 * 5;

	private static Logger log = LoggerFactory.getLogger(BucketRepository.class);

	private Map<String, Bucket> buckets = new HashMap<>(MAX_BUCKET_COUNT * 2);

	public synchronized Bucket putBucket(String bucketName, String data)
	{
		if (!this.buckets.containsKey(bucketName) && this.buckets.size() >= MAX_BUCKET_COUNT)
		{
			throw new IllegalStateException("No bucket slot available. Retry later.");
		}

		long now = System.currentTimeMillis();
		Bucket bucket = new Bucket(bucketName, data, now + MILLIS_UNTIL_EXPIRATION_AFTER_CREATION);
		buckets.put(bucketName, bucket);

		log.info("Bucket {} added.", bucketName);

		return bucket;
	}

	public synchronized Bucket getBucket(String bucketName)
	{
		long now = System.currentTimeMillis();
		Bucket bucket = buckets.get(bucketName);
		if (bucket == null || bucket.hasExpiredAt(now))
		{
			log.info("Accessed bucket {} not found/ has expired.", bucketName);
			return null;
		}

		bucket.setExpiryTimeMillis(now + MILLIS_UNTIL_EXPIRY_AFTER_ACCESS);
		log.info("Bucket {} accessed.", bucketName);

		return bucket;
	}

	public synchronized void evictBuckets()
	{
		int bucketsBeforeEviction = this.buckets.size();

		long now = System.currentTimeMillis();
		for (Iterator<Map.Entry<String, Bucket>> iter = this.buckets.entrySet().iterator();
			 iter.hasNext(); )
		{
			Map.Entry<String, Bucket> entry = iter.next();
			if (entry.getValue().hasExpiredAt(now))
			{
				String bucketName = entry.getKey();

				iter.remove();

				log.debug("Bucket {} evicted.", bucketName);
			}
		}

		log.info("{} bucket(s) in use after eviction, {} removed.", this.buckets.size(), bucketsBeforeEviction - this.buckets.size());
	}
}
