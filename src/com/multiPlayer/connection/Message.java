package com.multiPlayer.connection;

import java.io.Serializable;

// класс, отвечающий за пересылаемые сообщения.
public class Message implements Serializable {
    private final MessageType type;
    private final Serializable data;

    public Message(MessageType type) {
        this.type = type;
        this.data = null;
    }

    public Message(MessageType type, Serializable data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public Serializable getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", data='" + data + '\'' +
                '}';
    }
}
