package com.example.greenteam.eventfulevents.Model;

/**
 * Created by NB on 2016-11-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.greenteam.eventfulevents.Model.EventfulEvent;

import java.util.ArrayList;

public class Events {

    @SerializedName("event")
    @Expose
    private ArrayList<EventfulEvent> event = new ArrayList<>();

    public ArrayList<EventfulEvent> getEvent() {
        return event;
    }
}