package com.example.greenteam.eventfulevents.Utility;

import android.app.Application;

import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

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

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .rxFactory(new RealmObservableFactory())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
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
