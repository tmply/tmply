package com.github.cbuschka.tmply.controllers;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.cbuschka.tmply.business.DeliverNotification;
import com.github.cbuschka.tmply.business.PublishRequest;
import com.github.cbuschka.tmply.business.StatsNotification;
import com.github.cbuschka.tmply.business.SubscribeRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(value = {@JsonSubTypes.Type(name = "subscribe", value = SubscribeRequest.class),
		@JsonSubTypes.Type(name = "deliver", value = DeliverNotification.class),
		@JsonSubTypes.Type(name = "stats", value = StatsNotification.class),
		@JsonSubTypes.Type(name = "publish", value = PublishRequest.class)})
public abstract class WebSocketMessage
{
	protected WebSocketMessage()
	{
	}
}
