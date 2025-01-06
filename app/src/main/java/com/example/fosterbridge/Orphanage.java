package com.example.fosterbridge;

public class Orphanage {
    private String name;
    private String location;
    private String username;


    // No-argument constructor (required by Firestore)
    public Orphanage() {
    }

    // Constructor with parameters
    public Orphanage(String name, String location, String username) {
        this.name = name;
        this.location = location;
        this.username = username;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
