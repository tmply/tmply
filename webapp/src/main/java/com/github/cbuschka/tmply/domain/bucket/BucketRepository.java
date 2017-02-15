package com.github.cbuschka.tmply.domain.bucket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BucketRepository extends JpaRepository<BucketEntity, Long>
{
	long count();

	BucketEntity findByBucketName(String bucketName);

	void deleteByExpiryTimeLessThan(Date expiryTime);

	void deleteByBucketName(String bucketName);
}
