package com.work2home.publica.project.rest.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageController {

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
        String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
        
        System.out.println("ALow");
        
        if (StringUtils.isNotBlank(userName)) {
            logger.info("New User: {}, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
                    userName, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());

            clientMap.put(userName, socketIOClient.getSessionId());
        }
    }

    @OnDisconnect
    public void onDisConnect(SocketIOClient socketIOClient) {
    	System.out.println("falow");
    	
        String userName = socketIOClient.getHandshakeData().getSingleUrlParam("userName");
        if (StringUtils.isNotBlank(userName)) {
            logger.info("User Disconnected: {}, NettySocketSessionId: {}, NettySocketRemoteAddress: {}",
                    userName, socketIOClient.getSessionId().toString(), socketIOClient.getRemoteAddress().toString());

            clientMap.remove(userName);

        }
    }
    
    @OnEvent("message")
    public void sendMsg(SocketIOClient socketIOClient, AckRequest ackRequest, Object obj) {
        System.out.println("RECEBI PORRAAA!");
    }
}