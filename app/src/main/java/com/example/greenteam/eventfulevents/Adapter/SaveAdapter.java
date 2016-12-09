package com.example.greenteam.eventfulevents.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.DatabaseHelper.DBEvent;

import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.MyViewHolder> {

    Context context;
    private View.OnClickListener    onClickListener;
    private List<DBEvent>           dbEvents;

    public SaveAdapter(Context context, List<DBEvent> dbEvents, View.OnClickListener onClickListener) {

        this.onClickListener    = onClickListener;
        this.dbEvents           = dbEvents;
        this.context            = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DBEvent item    = dbEvents.get(position);

        holder.llMain.setTag(item);
        holder.title.setText(item.title);
        holder.venue.setText(item.venue_name);
        holder.date.setText(item.start_time);

        if (item.url != null)
        {
            Glide.with(context).load(item.url).placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return dbEvents.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout    llMain;
        TextView        title;
        TextView        date;
        TextView        venue;
        ImageView       ivImage;
        
        public MyViewHolder(View v)
        {

            super(v);
            llMain          = (LinearLayout) v.findViewById(R.id.llMain);
            llMain.setOnClickListener(onClickListener);
            title           = (TextView) v.findViewById(R.id.tvTitle);
            date            = (TextView) v.findViewById(R.id.tvDate);
            venue           = (TextView) v.findViewById(R.id.tvVenue);
            ivImage         = (ImageView) v.findViewById(R.id.ivImage);
        }
    }
}