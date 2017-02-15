package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.bucket.BucketDomainService;
import com.github.cbuschka.tmply.domain.bucket.BucketRepository;
import com.github.cbuschka.tmply.domain.session.WebSocketSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Service
public class BroadcastStatsBusinessService
{
	@Autowired
	private BucketRepository bucketRepository;
	@Autowired
	private WebSocketSessionRepository webSocketSessionRepository;
	@Autowired
	private DeliverBucketBusinessService deliverBucketBusinessService;

	@Scheduled(initialDelay = 1000, fixedDelay = 1000 * 5)
	public void broadcastStats()
	{
		int bucketsInUseCount = (int) this.bucketRepository.count();
		int maxBucketsCount = BucketDomainService.MAX_BUCKET_COUNT;
		StatsNotification statsNotification = new StatsNotification(maxBucketsCount, maxBucketsCount - bucketsInUseCount);
		List<WebSocketSession> sessions = this.webSocketSessionRepository.getSessions();
		for (WebSocketSession session : sessions)
		{
			this.deliverBucketBusinessService.sendTo(statsNotification, session);
		}
	}
}
