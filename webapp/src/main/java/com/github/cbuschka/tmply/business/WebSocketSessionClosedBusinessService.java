package com.github.cbuschka.tmply.business;

import com.github.cbuschka.tmply.domain.session.WebSocketSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WebSocketSessionClosedBusinessService
{
	@Autowired
	private WebSocketSessionRepository webSocketSessionRepository;

	public void closed(WebSocketSession webSocketSession)
	{
		this.webSocketSessionRepository.remove(webSocketSession);
	}

	public void openend(WebSocketSession session)
	{
		this.webSocketSessionRepository.add(session);
	}
}
