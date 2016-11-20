package com.example.greenteam.eventfulevents.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greenteam.eventfulevents.Model.EventModel;
import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.Adapter.SearchAdapter;
import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;
import com.example.greenteam.eventfulevents.Utility.MyApplication;
import com.example.greenteam.eventfulevents.Utility.Utility;

public class MainActivity extends AppCompatActivity implements RetrofitUtil.OnLoadCallback, View.OnClickListener {

    private RecyclerView                rvSearchList;
    private TextInputEditText           etSearch;
    private RetrofitUtil.OnLoadCallback onLoadCallback;
    private EventModel                  events;
    private ProgressDialog              dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        onLoadCallback = this;
    }

    private void init() {

        rvSearchList    = (RecyclerView) findViewById(R.id.rvList);
        etSearch        = (TextInputEditText) findViewById(R.id.etSearch);

        rvSearchList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvSearchList.setItemAnimator(new DefaultItemAnimator());
        rvSearchList.setNestedScrollingEnabled(false);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    if (Utility.hasConnection(getApplicationContext()))
                    {
                        dialog = new ProgressDialog(MainActivity.this);
                        dialog.setMessage("Please wait...");
                        dialog.setCancelable(false);
                        dialog.show();
                        Utility.hideSoftKeyboard(MainActivity.this);

                        APIListService apiService = ((MyApplication) getApplicationContext()).getAPIListService();
                        ((MyApplication) getApplicationContext()).getRetrofitUtil().callAPI(
                                apiService.EventfulList("concert", etSearch.getText().toString()),
                                new EventModel(), onLoadCallback);
                    } else
                        Toast.makeText(getApplicationContext(), "No Internet Connection!",
                                Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    private void setAdapter(EventModel events) {
        if (events != null && events.events != null
                && events.events.event != null
                && events.events.event.size() != 0)
        {
            rvSearchList.setAdapter(new SearchAdapter(events.events.event,
                    getApplicationContext(), this));
        }
    }

    @Override
    public void onResponseSuccess(Object response)
    {
        dialog.dismiss();
        events = (EventModel) response;
        setAdapter(events);
    }

    @Override
    public void onResponseFailure(Object error) {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        int pos         = Integer.parseInt(v.getTag().toString());
        Intent intent   = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("eventTitle", events.events.event.get(pos).title);
        intent.putExtra("eventDesc", events.events.event.get(pos).description);
        startActivity(intent);
    }

}
