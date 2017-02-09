package com.github.cbuschka.tmply.business;

public class PublishBucketRequest
{
	private String bucketName;

	private String data;

	public PublishBucketRequest()
	{
	}

	public String getBucketName()
	{
		return bucketName;
	}

	public void setBucketName(String bucketName)
	{
		this.bucketName = bucketName;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
