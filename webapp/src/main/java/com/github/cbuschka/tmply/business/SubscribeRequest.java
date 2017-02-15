package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.controllers.WebSocketMessage;

public class SubscribeRequest extends WebSocketMessage
{
	private String bucketName;

	public SubscribeRequest()
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
}
