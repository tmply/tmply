package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.controllers.WebSocketMessage;

public class DeliverNotification extends WebSocketMessage
{
	private String bucketName;

	private String data;

	public DeliverNotification(String bucketName, String data)
	{
		this.bucketName = bucketName;
		this.data = data;
	}

	public DeliverNotification()
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
