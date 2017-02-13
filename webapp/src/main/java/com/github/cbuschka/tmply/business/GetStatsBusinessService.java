package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.BucketDomainService;
import com.github.cbuschka.tmply.domain.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GetStatsBusinessService
{
	@Autowired
	private BucketRepository bucketRepository;

	@Autowired
	private BucketDomainService bucketDomainService;

	public StatsDto getStats()
	{
		int bucketsInUseCount = (int)this.bucketRepository.count();
		int maxBucketsCount = BucketDomainService.MAX_BUCKET_COUNT;
		return new StatsDto(maxBucketsCount, bucketsInUseCount);
	}
}
