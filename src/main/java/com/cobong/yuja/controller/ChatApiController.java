package com.cobong.yuja.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.cobong.yuja.config.websocket.SocketMessageReceiveDto;
import com.cobong.yuja.config.websocket.SocketMessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatApiController {
	private final SimpMessagingTemplate msgTemplate;
	private final SocketMessageService socketMessageService;
	
	@MessageMapping("/chat/send")
	public void sendMsg(SocketMessageReceiveDto msg) {
		String receiver = msg.getReceiver();
		socketMessageService.save(msg);
		msgTemplate.convertAndSend("/topic/cobong/"+receiver, msg);
	}
	
	/* 
	@MessageMapping("/chat/send")
	@SendTo("/topic/cobong")
	public void sendMsg(@Payload SocketMessage msg) {
		msgTemplate.convertAndSend("/topic/cobong", msg);
	}
	
	@MessageMapping("/chat/join")
	@SendTo("/topic/cobong")
	public SocketMessage join(@Payload SocketMessage msg, SimpMessageHeaderAccessor smha) {
		smha.getSessionAttributes().put("username", "Sender");
		return msg;
	}
	 * */
}
