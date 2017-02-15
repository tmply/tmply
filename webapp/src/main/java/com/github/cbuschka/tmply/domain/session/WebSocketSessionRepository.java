package com.github.cbuschka.tmply.domain.session;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WebSocketSessionRepository
{
	private Map<WebSocketSession, String> subscriptionByWebSocketSession = new HashMap<>();
	private Map<String, WebSocketSession> webSocketSessionByBucketName = new HashMap<>();

	public synchronized void remove(WebSocketSession webSocketSession)
	{
		String bucketName = subscriptionByWebSocketSession.remove(webSocketSession);
		if (bucketName != null)
		{
			this.webSocketSessionByBucketName.remove(bucketName);
		}
	}

	public synchronized void subscribe(WebSocketSession webSocketSession, String bucketName)
	{
		WebSocketSession subscribedWebSocketSession = this.webSocketSessionByBucketName.get(bucketName);
		if (subscribedWebSocketSession != null && !subscribedWebSocketSession.getId().equals(webSocketSession.getId()))
		{
			throw new IllegalStateException("Bucket " + bucketName + " already has a subscriber.");
		}

		this.subscriptionByWebSocketSession.put(webSocketSession, bucketName);
		this.webSocketSessionByBucketName.put(bucketName, webSocketSession);
	}

	public synchronized WebSocketSession getSubscriber(String bucketName)
	{
		return this.webSocketSessionByBucketName.get(bucketName);
	}

	public synchronized void unsubscribe(String bucketName, WebSocketSession subscriber)
	{
		String subscription = subscriptionByWebSocketSession.get(subscriber);
		if (subscription != null)
		{
			this.webSocketSessionByBucketName.remove(subscription);
		}
	}

	public synchronized List<WebSocketSession> getSessions()
	{
		List<WebSocketSession> sessions = new ArrayList<>(this.subscriptionByWebSocketSession.keySet());
		return sessions;
	}

	public synchronized void add(WebSocketSession session)
	{
		this.subscriptionByWebSocketSession.put(session, null);
	}
}
