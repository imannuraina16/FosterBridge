package com.example.fostermessages.utils;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class firebaseUtil {

    public static DocumentReference currentUserDetail(){
        return FirebaseFirestore.getInstance().collection("orphanage").document();
    }

    public static String currentUserID(){
        return FirebaseAuth.getInstance.
    }

    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("orphanage");
    }

    public static DocumentReference getChatroomReference(String chatroomID){
        return FirebaseFirestore.getInstance().collection("chatroom").document(chatroomID);
    }

    public static String getChatroomID(String userID1,String userID2){
        return userID1+"_"+userID2;
    }
}
