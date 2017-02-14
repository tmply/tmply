package com.github.cbuschka.tmply.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketController extends TextWebSocketHandler {

    private static Logger log = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Session {} established.", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Session {} closed with status {}.", session.getId(), status);
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        log.info("Got message {}.", textMessage);
    }
}
