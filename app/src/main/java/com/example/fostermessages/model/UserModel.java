package com.example.fostermessages.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserModel {
    private String phone;
    private String username;
    private Timestamp createTime;

    public UserModel() {
    }

    public UserModel(String phone, String username, Timestamp createTime) {
        this.phone = phone;
        this.username = username;
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return FirebaseFirestore.getInstance().collection("orphanage").getId();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
