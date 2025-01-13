package com.example.fosterbridge;

public class VolunteerProfile {
    private String id;
    private String event_name;
    private String event_description;
    private String date;
    private String username;

    // Default constructor for Firestore
    public VolunteerProfile() {}

    public VolunteerProfile(String id, String event_name, String event_description, String date, String username) {
        this.id = id;
        this.event_name = event_name;
        this.event_description = event_description;
        this.date = date;
        this.username = username;
    }

    public VolunteerProfile(String event_name, String date, String event_description, String username){
        this.event_name = event_name;
        this.event_description = event_description;
        this.date = date;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_description() {
        return event_description;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public String getDate() {
        return date;  // Getter for the 'date' field
    }

    public void setDate(String date) {  // Setter for the 'date' field
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
