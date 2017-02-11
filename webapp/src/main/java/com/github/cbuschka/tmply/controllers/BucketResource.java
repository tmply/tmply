package com.github.cbuschka.tmply.controllers;

import com.github.cbuschka.tmply.domain.Bucket;
import com.github.cbuschka.tmply.business.ConsumeBucketBusinessService;
import com.github.cbuschka.tmply.business.PublishBucketBusinessService;
import com.github.cbuschka.tmply.business.PublishBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BucketResource
{
	@Autowired
	private ConsumeBucketBusinessService consumeBucketBusinessService;

	@Autowired
	private PublishBucketBusinessService publishBucketBusinessService;

	@RequestMapping(value = "/api/buckets/{bucketName}", method = RequestMethod.GET)
	public
	@ResponseBody
	ResponseEntity<Bucket> getBucket(@PathVariable("bucketName") String bucketName)
	{
		Bucket bucket = this.consumeBucketBusinessService.consume(bucketName);
		if (bucket == null)
		{
			delay();

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(bucket, HttpStatus.OK);
	}

	private void delay()
	{
		try
		{
			Thread.sleep(1000 * 3);
		}
		catch (InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}
	}

	@RequestMapping(value = "/api/buckets", method = RequestMethod.POST)
	public
	@ResponseBody
	ResponseEntity<Bucket> publishBucket(@RequestBody PublishBucketRequest request)
	{
		if (request.getBucketName() == null || request.getBucketName().trim().length() < 8)
		{
			throw new IllegalArgumentException("Invalid bucket name '" + request.getBucketName() + "'.");
		}

		if( request.getData() == null || request.getData().length() < 100 ) {

			throw new IllegalArgumentException("Invalid bucket data, is required and must be shorter than 100 characters.");
		}

		Bucket bucket = this.publishBucketBusinessService.publish(request);
		return new ResponseEntity<>(bucket, HttpStatus.OK);

	}
}
