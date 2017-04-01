package com.github.cbuschka.tmply.controllers;

import com.github.cbuschka.tmply.business.BucketDto;
import com.github.cbuschka.tmply.business.ConsumeBucketBusinessService;
import com.github.cbuschka.tmply.business.DeleteBucketBusinessService;
import com.github.cbuschka.tmply.business.PublishBucketBusinessService;
import com.github.cbuschka.tmply.config.AppConfig;
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
	private AppConfig appConfig;

	@Autowired
	private ConsumeBucketBusinessService consumeBucketBusinessService;

	@Autowired
	private PublishBucketBusinessService publishBucketBusinessService;

	@Autowired
	private DeleteBucketBusinessService deleteBucketBusinessService;

	@RequestMapping(value = "/api/buckets/{bucketName}", method = RequestMethod.DELETE)
	public
	@ResponseBody
	ResponseEntity<?> deleteBucket(@PathVariable("bucketName") String bucketName)
	{
		this.deleteBucketBusinessService.delete(bucketName);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/api/buckets/{bucketName}", method = RequestMethod.GET)
	public
	@ResponseBody
	ResponseEntity<BucketDto> getBucket(@PathVariable("bucketName") String bucketName)
	{
		BucketDto bucket = this.consumeBucketBusinessService.consume(bucketName);
		if (bucket == null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(bucket, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/buckets", method = RequestMethod.POST)
	public
	@ResponseBody
	ResponseEntity<BucketDto> publishBucket(@RequestBody BucketDto request)
	{
		checkBucketKey(request);
		checkBucketValue(request);

		BucketDto bucket = this.publishBucketBusinessService.publish(request);
		return new ResponseEntity<>(bucket, HttpStatus.OK);
	}

	private void checkBucketValue(BucketDto request)
	{
		if (request.getData() == null || request.getData().length() >= this.appConfig.maxBucketValueLength)
		{
			throw new IllegalArgumentException(String.format("Invalid bucket data, is required and must be shorter than %s characters.", this.appConfig.maxBucketValueLength));
		}
	}

	private void checkBucketKey(BucketDto request)
	{
		if (request.getBucketName() == null || request.getBucketName().trim().length() < appConfig.minBucketKeyLength || request.getBucketName().length() > appConfig.maxBucketKeyLength)
		{
			throw new IllegalArgumentException(String.format("Invalid bucket name '" + request.getBucketName() + "', must be at least %d characters long and shorter than %d.", appConfig.minBucketKeyLength, appConfig.maxBucketKeyLength+1));
		}
	}
}
