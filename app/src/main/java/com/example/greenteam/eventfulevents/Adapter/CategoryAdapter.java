package com.example.greenteam.eventfulevents.Adapter;

/**
 * Created by NB on 2016-11-24.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.greenteam.eventfulevents.Model.CategoriesModel;
import com.example.greenteam.eventfulevents.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<CategoriesModel.Category> categoryList;
    private Context context;
    private View.OnClickListener listener;

    public CategoryAdapter(List<CategoriesModel.Category> categoryList, Context context, View.OnClickListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llMain;
        TextView title;

        public CategoryViewHolder(View v) {
            super(v);
            llMain = (LinearLayout) v.findViewById(R.id.llMain);
            llMain.setOnClickListener(listener);
            title = (TextView) v.findViewById(R.id.tvTitle);
        }
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category,
                parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        holder.llMain.setTag(position);
        holder.title.setText(Html.fromHtml(categoryList.get(position).name));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}