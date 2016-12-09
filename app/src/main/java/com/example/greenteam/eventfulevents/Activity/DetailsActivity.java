package com.example.greenteam.eventfulevents.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
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
        String desc = getIntent().getExtras().get("eventDesc").toString();
        if (desc != null && desc.length() != 0)
            tvDesc.setText(Html.fromHtml(getIntent().getExtras().get("eventDesc").toString()));
        else
            tvDesc.setText("No Description");

        tvDesc.setMovementMethod(new ScrollingMovementMethod());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        tvDesc  = (TextView) findViewById(R.id.tvDescription);
        toolbar = (Toolbar)  findViewById(R.id.toolbar);
    }
}
