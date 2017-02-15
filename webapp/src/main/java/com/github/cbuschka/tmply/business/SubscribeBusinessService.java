package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.bucket.BucketDomainService;
import com.github.cbuschka.tmply.domain.bucket.BucketEntity;
import com.github.cbuschka.tmply.domain.session.WebSocketSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SubscribeBusinessService
{
	@Autowired
	private WebSocketSessionRepository webSocketSessionRepository;
	@Autowired
	private BucketDomainService bucketDomainService;
	@Autowired
	private DeliverBucketBusinessService deliverBucketBusinessService;

	public void addSubscription(WebSocketSession webSocketSession, String bucketName)
	{
		this.webSocketSessionRepository.subscribe(webSocketSession, bucketName);
		deliverBucketIfFilled(webSocketSession, bucketName);
	}

	private void deliverBucketIfFilled(WebSocketSession webSocketSession, String bucketName)
	{
		BucketEntity bucket = this.bucketDomainService.getBucket(bucketName);
		if (bucket != null)
		{
			this.deliverBucketBusinessService.deliver(bucket.getBucketName(), bucket.getData(), webSocketSession);
			this.bucketDomainService.remove(bucketName);
		}
	}
}
