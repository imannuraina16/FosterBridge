package com.example.fostermessages.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class chatRoomModel {
    String chatRoomID;
    List<String> userIDs;
    Timestamp lastMessageTime;
    String lastMessageSenderID;

    public chatRoomModel(){

    }

    public chatRoomModel(String lastMessageSenderID, Timestamp lastMessageTime, List<String> userIDs, String chatRoomID) {
        this.lastMessageSenderID = lastMessageSenderID;
        this.lastMessageTime = lastMessageTime;
        this.userIDs = userIDs;
        this.chatRoomID = chatRoomID;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }

    public Timestamp getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Timestamp lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public String getLastMessageSenderID() {
        return lastMessageSenderID;
    }

    public void setLastMessageSenderID(String lastMessageSenderID) {
        this.lastMessageSenderID = lastMessageSenderID;
    }
}
