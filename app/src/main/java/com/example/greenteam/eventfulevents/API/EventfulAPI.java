package com.example.greenteam.eventfulevents.API;


import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.greenteam.eventfulevents.Model.EventfulEvent;
import com.example.greenteam.eventfulevents.Model.EventfulModel;

/**
 * Created by NB on 2016-11-17.
 */

public interface EventfulAPI {

    @GET("json/events/search?app_key=R5Kp5grL2bb8CqVt")
    retrofit2.Call<EventfulModel> EventfulList(@Query("category") String keyword,
                                               @Query("location") String location);
}
