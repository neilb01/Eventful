package com.example.greenteam.eventfulevents.Retrofit.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIListService {

    @GET("json/events/search?app_key=R5Kp5grL2bb8CqVt")
    public Call<String> EventfulList(@Query("keywords") String keyword,
                                              @Query("location") String location);

}