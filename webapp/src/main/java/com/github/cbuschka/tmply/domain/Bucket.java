package com.github.cbuschka.tmply.domain;

public class Bucket
{
	private String bucketName;
	private String data;
	private long expiryTimeMillis;

	public Bucket(String bucketName, String data, long expiryTimeMillis)
	{
		this.bucketName = bucketName;
		this.data = data;
		this.expiryTimeMillis = expiryTimeMillis;
	}

	public String getBucketName()
	{
		return bucketName;
	}

	public String getData()
	{
		return data;
	}

	public synchronized boolean hasExpiredAt(long timeMillis)
	{
		return timeMillis >= this.expiryTimeMillis;
	}

	public synchronized void setExpiryTimeMillis(long expiryTimeMillis)
	{
		this.expiryTimeMillis = expiryTimeMillis;
	}
}
