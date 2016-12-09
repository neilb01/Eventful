package com.example.greenteam.eventfulevents.DatabaseHelper;

import io.realm.RealmObject;

public class DBEvent extends RealmObject {

    public String   title;
    public String   description;
    public String   start_time;
    public String   venue_name;
    public String   url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStarTime() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getVenueName() {
        return venue_name;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }

}
