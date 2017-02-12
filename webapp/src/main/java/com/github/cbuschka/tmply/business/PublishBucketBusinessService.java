package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.BucketEntity;
import com.github.cbuschka.tmply.domain.BucketDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PublishBucketBusinessService
{
	@Autowired
	private BucketDomainService bucketDomainService;

	public BucketDto publish(BucketDto request)
	{
		BucketEntity bucketEntity = this.bucketDomainService.putBucket(request.getBucketName(), request.getData());
		return new BucketDto(bucketEntity.getBucketName(), bucketEntity.getData());
	}
}
