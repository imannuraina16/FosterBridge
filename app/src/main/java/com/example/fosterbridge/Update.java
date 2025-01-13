package com.example.fosterbridge;
class Update {
    private String id;
    private String description;
    private String username;

    // No-argument constructor (required for Firestore deserialization)
    public Update() {
    }

    // Constructor with description only
    public Update(String description) {
        this.description = description;
    }

    // Getter and setter methods for each property
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
