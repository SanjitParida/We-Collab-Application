package com.s3.learning.chatapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.s3.learning.chatapp.constant.ChatConstants;
import com.s3.learning.chatapp.model.ChatMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get(ChatConstants.USERNAME);
        if(username != null) {
          //  log.info("User Disconnected : " + username);

            ChatMessage chatMessagePojo = new ChatMessage();
            chatMessagePojo.setType(ChatMessage.MessageType.LEAVE);
            chatMessagePojo.setSender(username);
            messagingTemplate.convertAndSend(ChatConstants.SEND_TO_URL, chatMessagePojo);
        }
    }
}
