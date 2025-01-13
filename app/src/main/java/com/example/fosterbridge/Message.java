package com.example.fosterbridge;

public class Message {
    private String text;
    private boolean isSent;

    // Constructor
    public Message(String text, boolean isSent) {
        this.text = text;
        this.isSent = isSent;
    }

    // Getter for message text
    public String getText() {
        return text;
    }

    // Getter to check if message is sent
    public boolean isSent() {
        return isSent;
    }
}

