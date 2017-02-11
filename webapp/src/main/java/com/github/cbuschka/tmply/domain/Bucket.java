package com.github.cbuschka.tmply.domain;

public class Bucket
{
	private String bucketName;
	private String data;
	private long evictionTimeMillis;

	public Bucket(String bucketName, String data)
	{
		this.bucketName = bucketName;
		this.data = data;
		this.evictionTimeMillis = System.currentTimeMillis()+(1000*60*5);
	}

	public String getBucketName()
	{
		return bucketName;
	}

	public String getData()
	{
		return data;
	}

	public boolean isEvictableAt(long timeMillis) {
		return timeMillis >= this.evictionTimeMillis;
	}

	public void setEvictionTimeMillis(long evictionTimeMillis)
	{
		this.evictionTimeMillis = evictionTimeMillis;
	}
}
