package com.example.greenteam.eventfulevents.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.greenteam.eventfulevents.Adapter.SaveAdapter;
import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.DatabaseHelper.RealmHelper;
import com.example.greenteam.eventfulevents.DatabaseHelper.DBEvent;

public class SaveActivity extends AppCompatActivity implements

    View.OnClickListener {

        private     RecyclerView    rvSaveList;
                    SaveAdapter             saveAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_saved_events);
            init();
        }

        private void init() {

            rvSaveList                                      = (RecyclerView) findViewById(R.id.rvSaveList);
            final LinearLayoutManager linearLayoutManager   = new LinearLayoutManager(getApplicationContext());
            rvSaveList.setLayoutManager(linearLayoutManager);
            rvSaveList.setItemAnimator(new DefaultItemAnimator());
            rvSaveList.setNestedScrollingEnabled(false);

        }

        @Override
        protected void onResume() {
            super.onResume();
            setAdapter();
        }

        private void setAdapter() {
            if (saveAdapter == null)
            {
                saveAdapter = new SaveAdapter(getApplicationContext(), RealmHelper.with(this).getDBEventList(), this);
            }
            rvSaveList.setAdapter(saveAdapter);
        }

        @Override
        public void onClick(View v) {

            DBEvent item        = (DBEvent) v.getTag();
            Intent intent       = new Intent(SaveActivity.this, DetailsActivity.class);
            intent.putExtra("eventTitle", item.title);
            intent.putExtra("venue_name", item.venue_name);
            intent.putExtra("start_time", item.start_time);
            intent.putExtra("image", item.url);
            intent.putExtra("eventDesc", item.description);

            startActivity(intent);
        }
}