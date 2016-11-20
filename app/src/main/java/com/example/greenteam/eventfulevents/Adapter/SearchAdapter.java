package com.example.greenteam.eventfulevents.Adapter;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.greenteam.eventfulevents.Model.EventModel;
import com.example.greenteam.eventfulevents.R;

import java.util.List;

/**
 * Created by NB on 2016-11-19.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>  {

    private List<EventModel.Events.Event> eventList;
    private Context context;
    private View.OnClickListener listener;

    public SearchAdapter(List<EventModel.Events.Event> eventList,
                         Context context,
                         View.OnClickListener listener) {

        this.eventList = eventList;
        this.context = context;
        this.listener = listener;
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llMain;
        TextView title;
        TextView date;
        TextView venue;
        ImageView ivImage;


        public SearchViewHolder(View v) {

            super(v);
            llMain = (LinearLayout) v.findViewById(R.id.llMain);
            llMain.setOnClickListener(listener);
            title       = (TextView) v.findViewById(R.id.tvTitle);
            date        = (TextView) v.findViewById(R.id.tvDate);
            venue       = (TextView) v.findViewById(R.id.tvVenue);
            ivImage     = (ImageView) v.findViewById(R.id.ivImage);

        }
    }


    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,
                false);
        return new SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {

        holder.llMain.setTag(position);
        holder.title.setText(eventList.get(position).title);
        holder.venue.setText(eventList.get(position).venue_name);
        holder.date.setText(eventList.get(position).start_time);

        if (eventList.get(position).image != null && eventList.get(position).image.medium != null)
            Glide.with(context).load(eventList.get(position).image.medium.url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(holder.ivImage);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

