package com.example.greenteam.eventfulevents.Activity;

/**
 * Created by NB on 2016-11-25.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.greenteam.eventfulevents.Model.CategoriesModel;
import com.example.greenteam.eventfulevents.R;
import com.example.greenteam.eventfulevents.Adapter.CategoryAdapter;
import com.example.greenteam.eventfulevents.Retrofit.Interface.APIListService;
import com.example.greenteam.eventfulevents.Retrofit.utility.RetrofitUtil;
import com.example.greenteam.eventfulevents.Utility.MyApplication;
import com.example.greenteam.eventfulevents.Utility.Utility;

public class CategoryActivity extends AppCompatActivity implements RetrofitUtil.OnLoadCallback,
                                                                   View.OnClickListener {

    private RecyclerView rvCategoryList;
    private CategoriesModel categoriesModel;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        init();


        if (Utility.hasConnection(getApplicationContext())) {
            dialog = new ProgressDialog(CategoryActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
            Utility.hideSoftKeyboard(CategoryActivity.this);

            APIListService apiService = ((MyApplication) getApplicationContext()).getAPIListService();
            ((MyApplication) getApplicationContext()).getRetrofitUtil().callAPI(apiService.CategoryList(),

                    new CategoriesModel(), this);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();

        }
    }

    private void init() {
        rvCategoryList = (RecyclerView) findViewById(R.id.rvList);

//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        rvCategoryList.setLayoutManager(linearLayoutManager);

        rvCategoryList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        rvCategoryList.setItemAnimator(new DefaultItemAnimator());
        rvCategoryList.setNestedScrollingEnabled(false);

    }


    private void setAdapter(CategoriesModel categoriesModel) {
        CategoryAdapter categoryAdapter = null;

        if (categoriesModel != null && categoriesModel.category != null && categoriesModel.category.size() != 0) {
            categoryAdapter = new CategoryAdapter(categoriesModel.category, getApplicationContext(), this);
            rvCategoryList.setAdapter(categoryAdapter);
        }
    }

    @Override
    public void onResponseSuccess(Object response) {
        dialog.dismiss();
        categoriesModel = (CategoriesModel) response;

        setAdapter(categoriesModel);
    }

    @Override
    public void onResponseFailure(Object error) {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        int pos = Integer.parseInt(v.getTag().toString());
        Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
        intent.putExtra("categoryTitle", categoriesModel.category.get(pos).name);
        intent.putExtra("categoryID", categoriesModel.category.get(pos).id);

        startActivity(intent);
    }
}