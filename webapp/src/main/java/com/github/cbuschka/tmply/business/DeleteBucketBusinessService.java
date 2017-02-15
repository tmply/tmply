package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.bucket.BucketDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeleteBucketBusinessService
{
	@Autowired
	private BucketDomainService bucketDomainService;

	public void delete(String bucketName)
	{
		this.bucketDomainService.remove(bucketName);
	}
}
