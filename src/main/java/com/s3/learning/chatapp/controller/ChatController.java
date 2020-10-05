package com.s3.learning.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import com.s3.learning.chatapp.constant.ChatConstants;
import com.s3.learning.chatapp.model.ChatMessage;

@RestController
public class ChatController {

    @MessageMapping(ChatConstants.SEND_MESSAGE)
    @SendTo(ChatConstants.SEND_TO_URL)
    public ChatMessage sendMessage(@Payload ChatMessage chatMessagePojo) {
        return chatMessagePojo;
    }

    @MessageMapping(ChatConstants.ADD_USER)
    @SendTo(ChatConstants.SEND_TO_URL)
    public ChatMessage addUser(@Payload ChatMessage chatMessagePojo, 
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put(ChatConstants.USERNAME, chatMessagePojo.getSender());
        return chatMessagePojo;
    }
}
