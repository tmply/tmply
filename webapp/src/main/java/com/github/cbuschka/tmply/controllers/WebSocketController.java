package com.github.cbuschka.tmply.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cbuschka.tmply.business.BucketDto;
import com.github.cbuschka.tmply.business.PublishBucketBusinessService;
import com.github.cbuschka.tmply.business.PublishRequest;
import com.github.cbuschka.tmply.business.SubscribeBusinessService;
import com.github.cbuschka.tmply.business.SubscribeRequest;
import com.github.cbuschka.tmply.business.WebSocketSessionClosedBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketController extends TextWebSocketHandler
{
	private static Logger log = LoggerFactory.getLogger(WebSocketController.class);

	@Autowired
	private WebSocketSessionClosedBusinessService webSocketSessionClosedBusinessService;
	@Autowired
	private SubscribeBusinessService subscribeBusinessService;
	@Autowired
	private PublishBucketBusinessService publishBucketBusinessService;
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception
	{
		this.webSocketSessionClosedBusinessService.openend(session);

		log.info("Session {} established.", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception
	{
		this.webSocketSessionClosedBusinessService.closed(session);

		log.info("Session {} closed with status {}.", session.getId(), status);
	}

	protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception
	{
		WebSocketMessage message = objectMapper.readerFor(WebSocketMessage.class).readValue(textMessage.getPayload());
		log.info("Got message {} from session {}.", message, session.getId());
		if (message instanceof SubscribeRequest)
		{
			SubscribeRequest subscribeRequest = (SubscribeRequest) message;
			this.subscribeBusinessService.addSubscription(session, subscribeRequest.getBucketName());
		}
		else if (message instanceof PublishRequest)
		{
			PublishRequest publishRequest = (PublishRequest) message;
			this.publishBucketBusinessService.publish(new BucketDto(publishRequest.getBucketName(), publishRequest.getData()));
		}
		else
		{
			log.debug("Unhandled message {} ignored.", message);
		}
	}
}
