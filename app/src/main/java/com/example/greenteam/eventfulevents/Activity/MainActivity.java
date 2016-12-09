package com.example.greenteam.eventfulevents.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenteam.eventfulevents.Model.EventModel;
import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.Adapter.SearchAdapter;
import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.utility.EndlessRecyclerViewScrollListener;
import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;
import com.example.greenteam.eventfulevents.Utility.MyApplication;
import com.example.greenteam.eventfulevents.Utility.Utility;

public class MainActivity extends AppCompatActivity implements RetrofitUtil.OnLoadCallback,
                                                               View.OnClickListener
{

    private RecyclerView                    rvSearchList;
    private TextInputEditText               etSearch;
    private RetrofitUtil.OnLoadCallback     onLoadCallback;
    private EventModel                      eventsMain;
    private ProgressDialog                  dialog;
    private Toolbar                         toolbar;

    SearchAdapter searchAdapter = null;

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        toolbar.setTitle(getIntent().getExtras().get("categoryTitle").toString());

        onLoadCallback = this;
    }

    private void init() {

        toolbar     =       (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvSearchList    =   (RecyclerView) findViewById(R.id.rvList);
        etSearch        =   (TextInputEditText) findViewById(R.id.etSearch);

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
                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    searchAdapter = null;
                    scrollListener.resetState();
                    rvSearchList.setAdapter(null);
                    if (etSearch.getText().toString().length() != 0)
                        callAPI(1);
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
        if (Utility.hasConnection(getApplicationContext()))
        {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            Utility.hideSoftKeyboard(MainActivity.this);

            APIListService apiService = ((MyApplication) getApplicationContext()).getAPIListService();
            ((MyApplication) getApplicationContext()).getRetrofitUtil().callAPI(apiService
                    .EventfulList(getIntent().getExtras().get("categoryID").toString(), etSearch
                            .getText().toString(), page + ""), new EventModel(), onLoadCallback);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection. Try again.", Toast
                    .LENGTH_SHORT).show();
        }
    }

    private void setAdapter(EventModel events)
    {
        if (searchAdapter == null)
        {
            //Log.v("Tag", "searchAdapter == null");
            if (events != null && events.events != null && events.events.event != null && events.events.event.size() != 0) {
                searchAdapter = new SearchAdapter(events.events.event, getApplicationContext(), this);
                eventsMain = events;
                rvSearchList.setAdapter(searchAdapter);
            }
        }
        else
        {
            //Log.v("Tag", "searchAdapter not null");
            if (events != null &&
                events.events != null &&
                events.events.event != null &&
                events.events.event.size() != 0)
            {

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

        int pos         =   Integer.parseInt(v.getTag().toString());
        Intent intent   =   new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("eventTitle", eventsMain.events.event.get(pos).title);

        if (eventsMain.events.event.get(pos).description != null)
        {
            intent.putExtra("eventDesc", eventsMain.events.event.get(pos).description);
        }
        else
        {
            intent.putExtra("eventDesc", "No Description");
        }
        startActivity(intent);
    }
}