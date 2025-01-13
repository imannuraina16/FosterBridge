package com.example.fosterbridge;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.Timestamp;

public class Donation {

    private String id;
    private String username;          // Donor's username
    private String amount;            // Donation amount as
    @ServerTimestamp
    private Timestamp timestamp;      // Donation timestamp (using Firestore's Timestamp)
    @PropertyName("orphanage_username")
    private String orphanage_username; // Orphanage's username

    // Default constructor (required for Firestore)
    public Donation() {}

    // Constructor
    public Donation(String username, String amount, Timestamp timestamp, String orphanageUsername) {
        this.username = username;
        this.amount = amount;
        this.timestamp = timestamp;
        this.orphanage_username = orphanageUsername;
    }

    // Getter and Setter for `username`
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for `amount`
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    // Getter and Setter for `timestamp` (Firestore Timestamp)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    // Getter and Setter for `orphanageUsername`
    public String getOrphanageUsername() {
        return orphanage_username;
    }

    public void setOrphanageUsername(String orphanageUsername) {
        this.orphanage_username = orphanageUsername;
    }

    // Getter and Setter for `id`
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
