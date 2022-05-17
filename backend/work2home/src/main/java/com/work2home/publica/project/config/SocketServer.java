package com.work2home.publica.project.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

import ch.qos.logback.core.net.server.ServerRunner;

@Component
@Order(1)
public class SocketServer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketServer(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... args) {
        logger.info("---------- NettySocket ----------");
        socketIOServer.start();
        logger.info("---------- NettySocket ----------");
    }
}