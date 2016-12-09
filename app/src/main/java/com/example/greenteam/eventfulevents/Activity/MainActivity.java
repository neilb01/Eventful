package com.example.greenteam.eventfulevents.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenteam.eventfulevents.Adapter.SearchAdapter;
import com.example.greenteam.eventfulevents.LocationServices.GPSTracker;
import com.example.greenteam.eventfulevents.LocationServices.LocationAddress;
import com.example.greenteam.eventfulevents.Model.EventModel;
import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.utility.EndlessRecyclerViewScrollListener;
import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;
import com.example.greenteam.eventfulevents.Utility.MyApplication;
import com.example.greenteam.eventfulevents.Utility.Utility;

public class MainActivity extends AppCompatActivity implements RetrofitUtil.OnLoadCallback,
        View.OnClickListener {

    private RecyclerView rvSearchList;
    private TextInputEditText etSearch;
    private RetrofitUtil.OnLoadCallback onLoadCallback;
    private EventModel eventsMain;
    private ProgressDialog dialog;
    private Toolbar toolbar;
    private String title;
    private String titlereplace;
    private String location;
    private GPSTracker gps;
    private FloatingActionButton fba;

    SearchAdapter searchAdapter = null;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

        title = getIntent().getStringExtra("categoryTitle");
        titlereplace = title.replace("&amp;", "&");
        toolbar.setTitle(titlereplace);

        onLoadCallback = this;

        fba = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fba.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());

                    if (location != null) {
                        callAPI2(1);
                    }

                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }
        });

    }

    private void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvSearchList = (RecyclerView) findViewById(R.id.rvList);
        etSearch = (TextInputEditText) findViewById(R.id.etSearch);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvSearchList.setLayoutManager(linearLayoutManager);
        rvSearchList.setItemAnimator(new DefaultItemAnimator());
        rvSearchList.setNestedScrollingEnabled(false);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                callAPI(++page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvSearchList.addOnScrollListener(scrollListener);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchAdapter = null;
                    scrollListener.resetState();
                    rvSearchList.setAdapter(null);
                    if (etSearch.getText().toString().length() != 0)
                        callAPI2(1);
                    else
                        rvSearchList.setAdapter(null);
                    return true;
                }
                return false;
            }
        });
    }

    private void callAPI(int page) {
        //Log.v("Tag", "Call=>" + page);
        if (Utility.hasConnection(getApplicationContext())) {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            Utility.hideSoftKeyboard(MainActivity.this);

            APIListService apiService = ((MyApplication) getApplicationContext()).getAPIListService();
            ((MyApplication) getApplicationContext()).getRetrofitUtil().callAPI(apiService
                    .EventfulList(getIntent().getExtras().get("categoryID").toString(), etSearch
                            .getText().toString(), page + ""), new EventModel(), onLoadCallback);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection. Try again.", Toast
                    .LENGTH_SHORT).show();
        }
    }

    private void setAdapter(EventModel events) {
        if (searchAdapter == null) {
            //Log.v("Tag", "searchAdapter == null");
            if (events != null && events.events != null && events.events.event != null && events.events.event.size() != 0) {
                searchAdapter = new SearchAdapter(events.events.event, getApplicationContext(), this);
                eventsMain = events;
                rvSearchList.setAdapter(searchAdapter);
            }
        } else {
            //Log.v("Tag", "searchAdapter not null");
            if (events != null &&
                    events.events != null &&
                    events.events.event != null &&
                    events.events.event.size() != 0) {

                eventsMain.events.event.addAll(events.events.event);
                searchAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResponseSuccess(Object response) {

        dialog.dismiss();
        EventModel events = (EventModel) response;
        setAdapter(events);
    }

    @Override
    public void onResponseFailure(Object error) {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        int pos = Integer.parseInt(v.getTag().toString());
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("eventTitle", eventsMain.events.event.get(pos).title);
        intent.putExtra("venue_name", eventsMain.events.event.get(pos).venue_name);
        intent.putExtra("start_time", eventsMain.events.event.get(pos).start_time);

        if (eventsMain.events.event.get(pos).image != null && eventsMain.events.event.get(pos).image.medium != null)
            intent.putExtra("image", eventsMain.events.event.get(pos).image.medium.url);
        else
            intent.putExtra("image","");

        if (eventsMain.events.event.get(pos).description != null) {
            intent.putExtra("eventDesc", eventsMain.events.event.get(pos).description);
        } else {
            intent.putExtra("eventDesc", "No Description");
        }
        startActivity(intent);
    }

    // Location function to send and set the GPS location to a stored variable
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            location = locationAddress;
        }
    }

    private void callAPI2(int page) {
        //Log.v("Tag", "Call=>" + page);
        if (Utility.hasConnection(getApplicationContext())) {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            Utility.hideSoftKeyboard(MainActivity.this);

            APIListService apiService = ((MyApplication) getApplicationContext()).getAPIListService();
            ((MyApplication) getApplicationContext()).getRetrofitUtil().callAPI(apiService
                    .EventfulList(getIntent().getExtras().get("categoryID").toString(),
                            location, page + ""), new EventModel(), this);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection. Try again.", Toast
                    .LENGTH_SHORT).show();
        }
    }
}