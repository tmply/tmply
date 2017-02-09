package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.Bucket;
import com.github.cbuschka.tmply.domain.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumeBucketBusinessService
{
	@Autowired
	private BucketRepository bucketRepository;

	public Bucket consume(String bucketName) {
		return this.bucketRepository.getBucket(bucketName);
	}
}
