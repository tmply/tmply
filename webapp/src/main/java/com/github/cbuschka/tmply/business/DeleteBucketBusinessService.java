package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.BucketDomainService;
import com.github.cbuschka.tmply.domain.BucketEntity;
import com.github.cbuschka.tmply.domain.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeleteBucketBusinessService
{
	@Autowired
	private BucketRepository bucketRepository;

	public void delete(String bucketName)
	{
		this.bucketRepository.deleteByBucketName(bucketName);
	}
}
