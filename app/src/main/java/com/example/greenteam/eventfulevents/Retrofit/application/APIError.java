package com.example.greenteam.eventfulevents.Retrofit.application;

public class APIError {

    private String message;

    public APIError() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}