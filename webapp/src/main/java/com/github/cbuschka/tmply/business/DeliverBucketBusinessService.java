package com.github.cbuschka.tmply.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cbuschka.tmply.controllers.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class DeliverBucketBusinessService
{
	@Autowired
	private ObjectMapper objectMapper;

	public void deliver(String bucketName, String data, WebSocketSession webSocketSession)
	{
		sendTo(new DeliverNotification(bucketName, data), webSocketSession);
	}

	public void sendTo(WebSocketMessage message, WebSocketSession receiverWebSocketSession)
	{
		try
		{
			String json = toJson(message);
			receiverWebSocketSession.sendMessage(new TextMessage(json));
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}

	private String toJson(WebSocketMessage message)
	{
		String json;
		try
		{
			json = this.objectMapper.writer().writeValueAsString(message);
		}
		catch (JsonProcessingException ex)
		{
			throw new RuntimeException(ex);
		}
		return json;
	}

}
