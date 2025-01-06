package com.example.fosterbridge;

public class Event {
    private String event_name;
    private String orphanage_id;
    private String event_description;

    private String orphanage_name;

    private String event_id;

    private String date;

    // Required empty constructor for Firestore
    public Event() {}

    public Event(String event_name, String orphanage_id, String event_description, String orphanage_name, String date, String event_id) {
        this.event_name = event_name;
        this.orphanage_id = orphanage_id;
        this.event_description = event_description;
        this.orphanage_name = orphanage_name;
        this.date = date;
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getOrphanage_id() {
        return orphanage_id;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getOrphanage_name() {
        return orphanage_name;
    }

    public void setOrphanage_name(String orphanage_name) {
        this.orphanage_name = orphanage_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
