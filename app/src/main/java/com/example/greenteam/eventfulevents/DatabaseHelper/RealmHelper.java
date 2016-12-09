package com.example.greenteam.eventfulevents.DatabaseHelper;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


public class RealmHelper {


    private static RealmHelper  instance;
    private final Realm         realm;

    public RealmHelper(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmHelper with(Fragment fragment) {

        if (instance == null)
        {
            instance = new RealmHelper(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmHelper with(Activity activity) {

        if (instance == null)
        {
            instance = new RealmHelper(activity.getApplication());
        }
        return instance;
    }

    public static RealmHelper with(Application application) {

        if (instance == null)
        {
            instance = new RealmHelper(application);
        }
        return instance;
    }

    public static RealmHelper getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.isAutoRefresh();
    }

    public void addDBEvent(DBEvent event) {

        realm.beginTransaction();
        realm.copyToRealm(event);
        realm.commitTransaction();
    }

    public RealmResults<DBEvent> getDBEventList() {

        RealmResults<DBEvent> query = realm.where(DBEvent.class).findAll();
        query = query.sort("title");
        return query;
    }

    public int getSearchDBEventListSize(String searchName) {
        return realm.where(DBEvent.class).contains("title", searchName.toUpperCase(), Case.INSENSITIVE).findAll().size();

    }
    public void deleteEvent(String title) {

        final RealmResults<DBEvent> results = realm.where(DBEvent.class).equalTo("title", title).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });

    }
}
