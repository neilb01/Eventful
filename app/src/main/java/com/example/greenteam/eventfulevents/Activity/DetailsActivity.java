package com.example.greenteam.eventfulevents.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.DatabaseHelper.RealmHelper;
import com.example.greenteam.eventfulevents.DatabaseHelper.DBEvent;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvDesc;
    private FloatingActionButton fbSave;

    String eventTitle, eventDesc, start_time, venue_name,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        eventTitle = getIntent().getExtras().get("eventTitle").toString();
        start_time = getIntent().getExtras().get("start_time").toString();
        venue_name = getIntent().getExtras().get("venue_name").toString();

        image = getIntent().getExtras().get("image").toString();

        if (RealmHelper.with(DetailsActivity.this).getSearchDBEventListSize(eventTitle) != 0) {
            fbSave.setTag("1");
            fbSave.setImageResource(R.drawable.ic_save);
        }
        toolbar.setTitle(eventTitle);

        String desc = getIntent().getExtras().get("eventDesc").toString();
        if (desc != null && desc.length() != 0)
            eventDesc = getIntent().getExtras().get("eventDesc").toString();
        else
            eventDesc = "No Description";
        tvDesc.setText(Html.fromHtml(eventDesc));

        tvDesc.setMovementMethod(new ScrollingMovementMethod());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        tvDesc = (TextView) findViewById(R.id.tvDescription);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fbSave = (FloatingActionButton) findViewById(R.id.fbSave);
        fbSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(fbSave.getTag().equals("1"))
        {
            fbSave.setTag("0");
            fbSave.setImageResource(R.drawable.ic_unsave);
            RealmHelper.with(DetailsActivity.this).deleteEvent(eventTitle);
        }else
        {
            DBEvent dbEvent = new DBEvent();
            dbEvent.setTitle(eventTitle);
            dbEvent.setDescription(eventDesc);
            dbEvent.setStart_time(start_time);
            dbEvent.setVenue_name(venue_name);
            dbEvent.setUrl(image);
            RealmHelper.with(DetailsActivity.this).addDBEvent(dbEvent);

            fbSave.setTag("1");
            fbSave.setImageResource(R.drawable.ic_save);
        }
    }
}
