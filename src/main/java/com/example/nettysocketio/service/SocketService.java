package com.example.nettysocketio.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.nettysocketio.model.Message;
import com.example.nettysocketio.util.MessageType;
import com.example.nettysocketio.util.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketService {

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
        for (
                SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.TEXT, Sender.SYSTEM, message));
            }
        }
    }

}
