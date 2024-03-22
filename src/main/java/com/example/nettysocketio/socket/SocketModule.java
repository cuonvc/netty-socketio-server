package com.example.nettysocketio.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.nettysocketio.filter.TokenProvider;
import com.example.nettysocketio.model.Message;
import com.example.nettysocketio.service.SocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {


    private final SocketIOServer server;
    private final SocketService socketService;

    @Autowired
    private TokenProvider tokenProvider;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
    }


    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            socketService.sendMessage(
                    senderClient.getHandshakeData().getSingleUrlParam("room"),
                    "get_message",
                    senderClient,
                    data.getMessage()
            );
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            String token = client.getHandshakeData().getSingleUrlParam("token");
            if (tokenProvider.validateToken(token).equals("PASSED")) {
                client.joinRoom(room);
                log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
            } else {
                client.disconnect();
            }
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

}
