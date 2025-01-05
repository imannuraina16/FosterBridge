package com.example.fostermessages.utils;

import android.content.Intent;

import com.example.fostermessages.model.UserModel;
import com.google.firebase.firestore.auth.User;

public class androidUtil {

    public static void passUserModelasIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
    }

    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        return userModel;
    }

}
