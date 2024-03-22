package com.example.nettysocketio.model;

import com.example.nettysocketio.util.MessageType;
import com.example.nettysocketio.util.Sender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private MessageType messageType;

    private Sender sender;

    private String message;

}
