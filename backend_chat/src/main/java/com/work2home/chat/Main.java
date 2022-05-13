package com.work2home.chat;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9090);
        
        System.out.println("Listening on port 9090");
        
        final SocketIOServer server = new SocketIOServer(config);
        
        server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				System.out.println("Id conectado: " + client.getSessionId());
			}
		});
        
        server.addEventListener("message", String.class, new DataListener<Message>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				System.out.println("ALOW");
				System.out.println(data);
			}
        }, null);

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
	}
}
