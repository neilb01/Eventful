package com.example.greenteam.eventfulevents.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.greenteam.eventfulevents.R;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        init();

        toolbar.setTitle(getIntent().getExtras().get("eventTitle").toString());
        tvDesc.setText(Html.fromHtml(getIntent().getExtras().get("eventDesc").toString()));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {

        tvDesc  = (TextView) findViewById(R.id.tvDescription);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

}
