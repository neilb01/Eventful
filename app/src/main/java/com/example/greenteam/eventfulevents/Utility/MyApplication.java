package com.example.greenteam.eventfulevents.Utility;

import android.app.Application;

import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;

/**
 * Created by NB on 2016-11-19.
 */

public class MyApplication extends Application {

    private RetrofitUtil retrofitUtil;
    private static MyApplication mInstance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
        retrofitUtil = new RetrofitUtil();
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RetrofitUtil getRetrofitUtil() {
        return retrofitUtil;
    }

    public APIListService getAPIListService() {
        return retrofitUtil.getAPIListService();
    }
}
