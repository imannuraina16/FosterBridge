package com.example.fosterbridge;

public class WishlistItem {
    private String id;
    private String description;
    private String username;

    // Default constructor for Firestore
    public WishlistItem() {}

    public WishlistItem(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
