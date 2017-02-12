package com.github.cbuschka.tmply.business;

public class BucketDto
{
	private String bucketName;

	private String data;


	public BucketDto() {}

	public BucketDto(String bucketName, String data)
	{
		this.bucketName = bucketName;
		this.data = data;
	}

	public void setBucketName(String bucketName)
	{
		this.bucketName = bucketName;
	}

	public String getBucketName()
	{
		return bucketName;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getData()
	{
		return data;
	}
}
