package com.work2home.publica.project.rest.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import com.work2home.publica.project.model.MessageDto;
import com.work2home.publica.project.model.Usuario;
import com.work2home.publica.project.service.MessageService;
import com.work2home.publica.project.utils.JwtUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageController {
	
	@Autowired
	private MessageService messageService;

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    private Map<String, UUID> clientMap = new ConcurrentHashMap<>(16);

    public Map<String, UUID> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<String, UUID> clientMap) {
        this.clientMap = clientMap;
    }

    private final SocketIOServer socketIOServer;

    @Autowired
    public MessageController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @OnConnect
    public void onConnect(SocketIOClient socketIOClient) {
        String userId = socketIOClient.getHandshakeData().getSingleUrlParam("userId");
        
        if (StringUtils.isNotBlank(userId)) {
            logger.info("New User: {}, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
            		userId, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());

            clientMap.put(userId, socketIOClient.getSessionId());
        }
    }

    @OnDisconnect
    public void onDisConnect(SocketIOClient socketIOClient) {    	
        String userId = socketIOClient.getHandshakeData().getSingleUrlParam("userId");
        if (StringUtils.isNotBlank(userId)) {
            logger.info("User Disconnected: {}, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
            		userId, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());
            clientMap.remove(userId);
        }
    }
    
    @OnEvent("message")
    public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest, MessageDto msg) {
    	logger.info(msg.toString());
        if (msg != null) {
            clientMap.forEach((key, value) -> {
                if (value != null && Integer.parseInt(key) == msg.getUserTo() || Integer.parseInt(key) == msg.getUserFrom()) {
                	socketIOServer.getClient(value).sendEvent("receiveMsg", msg);
                }
            });
        }
    } 
}