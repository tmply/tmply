package com.github.cbuschka.tmply.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
public class BucketRepository
{
	private Map<String, Bucket> buckets = new HashMap<>();

	public synchronized Bucket putBucket(String bucketName, String data)
	{
		Bucket bucket = new Bucket(bucketName, data);
		buckets.put(bucketName, bucket);
		return bucket;
	}

	public synchronized Bucket getBucket(String bucketName)
	{
		long now = System.currentTimeMillis();
		Bucket bucket = buckets.get(bucketName);
		if( bucket != null )
		{
			bucket.setEvictionTimeMillis(now + (1000 * 60));
		}
		return bucket;
	}

	public synchronized void evictBuckets()
	{
		long now = System.currentTimeMillis();
		for (Iterator<Map.Entry<String, Bucket>> iter = this.buckets.entrySet().iterator();
			 iter.hasNext(); )
		{
			Map.Entry<String, Bucket> entry = iter.next();
			if (entry.getValue().isEvictableAt(now))
			{
				iter.remove();
			}
		}
	}
}
