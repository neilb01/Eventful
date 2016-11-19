package com.example.greenteam.eventfulevents.API;

import retrofit.http.GET;
import retrofit.http.Query;

import com.example.greenteam.eventfulevents.Model.EventfulEvent;
import com.example.greenteam.eventfulevents.Model.EventfulModel;
import com.squareup.okhttp.Call;

/**
 * Created by NB on 2016-11-17.
 */

public interface EventfulAPI {

    @GET("json/events/search?app_key=R5Kp5grL2bb8CqVt")
    retrofit.Call<EventfulModel> EventfulList(@Query("keywords") String keyword,
                                              @Query("location") String location);
}
